package com.bittecsoluciones.restaurantepos.Service;

import com.bittecsoluciones.restaurantepos.DTOs.OrderItemResponseDTO;

import java.util.List;

public interface OrderItemService {
    OrderItemResponseDTO findById(Long id);
    List<OrderItemResponseDTO> listByOrder(Long orderId);
    OrderItemResponseDTO updateStatus(Long id, String status);
    OrderItemResponseDTO assignChef(Long itemId, Long chefId);
}
