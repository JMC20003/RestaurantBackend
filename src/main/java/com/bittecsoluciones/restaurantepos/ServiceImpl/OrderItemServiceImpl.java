package com.bittecsoluciones.restaurantepos.ServiceImpl;

import com.bittecsoluciones.restaurantepos.DTOs.OrderItemResponseDTO;
import com.bittecsoluciones.restaurantepos.Repository.OrderItemRepository;
import com.bittecsoluciones.restaurantepos.Repository.UserRepository;
import com.bittecsoluciones.restaurantepos.Service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public OrderItemResponseDTO findById(Long id) {
        var item = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));
        return OrderItemResponseDTO.from(item);
    }

    public List<OrderItemResponseDTO> listByOrder(Long orderId) {
        return orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(OrderItemResponseDTO::from)
                .toList();
    }

    public OrderItemResponseDTO updateStatus(Long id, String status) {
        var item = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        item.setStatus(status);

        if (status.equalsIgnoreCase("PREPARING")) {
            item.setPreparedAt(LocalDateTime.now());
        } else if (status.equalsIgnoreCase("SERVED")) {
            item.setServedAt(LocalDateTime.now());
        }

        var saved = orderItemRepository.save(item);

        // Notificar cambios de estado del item
        messagingTemplate.convertAndSend("/topic/kitchen/items", OrderItemResponseDTO.from(saved));

        return OrderItemResponseDTO.from(saved);
    }

    public OrderItemResponseDTO assignChef(Long itemId, Long chefId) {
        var item = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        var chef = userRepository.findById(chefId)
                .orElseThrow(() -> new RuntimeException("Chef no encontrado"));

        item.setChef(chef);
        var saved = orderItemRepository.save(item);

        // 🔔 Notificar que se asignó chef
        messagingTemplate.convertAndSend("/topic/kitchen/items", OrderItemResponseDTO.from(saved));

        return OrderItemResponseDTO.from(saved);
    }
}
