package com.bittecsoluciones.restaurantepos.ServiceImpl;

import com.bittecsoluciones.restaurantepos.DTOs.OrderRequestDTO;
import com.bittecsoluciones.restaurantepos.DTOs.OrderResponseDTO;
import com.bittecsoluciones.restaurantepos.Entity.*;
import com.bittecsoluciones.restaurantepos.Repository.*;
import com.bittecsoluciones.restaurantepos.Service.OrderService;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final TableRestaurantRepository tableRepository;
    private final CustomerRepository customerRepository;
    private final AreaAttentionRepository areaAttentionRepository;
    private final ProductIngredientRepository productIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final InventoryMovementRepository inventoryMovementRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        var waiter = userRepository.findById(dto.waiterId())
                .orElseThrow(() -> new RuntimeException("Mozo no encontrado"));
        var table = dto.tableId() != null ? tableRepository.findById(dto.tableId()).orElse(null) : null;
        var customer = dto.customerId() != null ? customerRepository.findById(dto.customerId()).orElse(null) : null;
        var area = areaAttentionRepository.findById(dto.areaAttentionId())
                .orElseThrow(() -> new RuntimeException("Área de atención no encontrada"));

        var order = Order.builder()
                .orderNumber(dto.orderNumber())
                .table(table)
                .customer(customer)
                .waiter(waiter)
                .orderDate(LocalDateTime.now())
                .status("PENDING")
                .notes(dto.notes())
                .subtotal(BigDecimal.ZERO)
                .taxAmount(dto.taxAmount())
                .discountAmount(dto.discountAmount())
                .total(BigDecimal.ZERO)
                .areaAttention(area)
                .build();

        BigDecimal subtotal = BigDecimal.ZERO;
        var items = new HashSet<OrderItem>();

        // CREAR ORDER ITEMS (no se persisten aún)
        for (var itemDto : dto.items()) {
            var product = productRepository.findById(itemDto.productId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + itemDto.productId()));

            var itemSubtotal = itemDto.unitPrice().multiply(itemDto.quantity());
            subtotal = subtotal.add(itemSubtotal);

            var item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemDto.quantity().intValue())
                    .unitPrice(itemDto.unitPrice())
                    .subtotal(itemSubtotal)
                    .notes(itemDto.notes())
                    .status("PENDING")
                    .build();

            items.add(item);
        }

        order.setOrderItems(items);
        order.setSubtotal(subtotal);
        order.setTotal(subtotal.add(order.getTaxAmount()).subtract(order.getDiscountAmount()));

        // guardamos orden y detalles (cascade ALL -> detalles se persisten)
        var savedOrder = orderRepository.save(order);

        // ------------------------------
        // Crear InventoryMovements (SALIDA) por cada OrderItem
        // ------------------------------
        for (OrderItem oi : savedOrder.getOrderItems()) {
            // obtenemos la receta: ingredientes que componen el producto
            List<ProductIngredient> recipe = productIngredientRepository.findByProductId(oi.getProduct().getId());

            for (ProductIngredient pi : recipe) {
                Ingredient ingredient = pi.getIngredient();

                // cantidad necesaria = cantidad requerida por 1 producto * cantidad pedida
                BigDecimal qtyPerProduct = pi.getQuantityRequired(); // asumo BigDecimal
                BigDecimal itemQty = BigDecimal.valueOf(oi.getQuantity());
                BigDecimal qtyToSubtract = qtyPerProduct.multiply(itemQty);

                // --- Validación de stock (opcional pero recomendable) ---
                if (ingredient.getCurrentStock() == null) {
                    ingredient.setCurrentStock(BigDecimal.ZERO);
                }
                if (ingredient.getCurrentStock().compareTo(qtyToSubtract) < 0) {
                    // puedes: (a) lanzar excepción para abortar la orden o (b) permitir negativo (no recomendado)
                    throw new RuntimeException("Stock insuficiente de '" + ingredient.getName()
                            + "'. Requerido: " + qtyToSubtract + ", disponible: " + ingredient.getCurrentStock());
                }

                // descontamos stock y guardamos ingrediente
                ingredient.setCurrentStock(ingredient.getCurrentStock().subtract(qtyToSubtract));
                ingredientRepository.save(ingredient);

                // creamos movimiento de inventario tipo SALIDA
                InventoryMovement movement = InventoryMovement.builder()
                        .ingredient(ingredient)
                        .movementType("Salida")
                        .quantity(qtyToSubtract)
                        .unitCost(ingredient.getCostPerUnit()) // o calcula un cost medio si lo prefieres
                        .referenceType("Order")
                        .referenceId(savedOrder.getId().intValue())
                        .notes("Salida por venta - Order " + savedOrder.getOrderNumber())
                        .user(savedOrder.getWaiter()) // el mozo que registró la orden
                        .movementDate(LocalDateTime.now())
                        .build();

                inventoryMovementRepository.save(movement);
            }
        }

        // notificar a cocina
        var response = OrderResponseDTO.from(savedOrder);
        messagingTemplate.convertAndSend("/topic/kitchen", response);

        return response;
    }

    @Override
    public OrderResponseDTO findById(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        return OrderResponseDTO.from(order);
    }

    @Override
    public List<OrderResponseDTO> listAll() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponseDTO::from)
                .toList();
    }

    @Override
    public OrderResponseDTO updateStatus(Long id, String status) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        order.setStatus(status);

        if (status.equalsIgnoreCase("SERVED")) {
            order.setServedAt(LocalDateTime.now());
        } else if (status.equalsIgnoreCase("CLOSED")) {
            order.setClosedAt(LocalDateTime.now());
        }

        var saved = orderRepository.save(order);

        // Notificar cambios
        messagingTemplate.convertAndSend("/topic/orders", OrderResponseDTO.from(saved));

        return OrderResponseDTO.from(saved);
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Orden no encontrada");
        }
        orderRepository.deleteById(id);
    }
}
