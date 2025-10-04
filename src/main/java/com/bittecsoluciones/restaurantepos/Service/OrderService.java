package com.bittecsoluciones.restaurantepos.Service;

import com.bittecsoluciones.restaurantepos.DTOs.OrderRequestDTO;
import com.bittecsoluciones.restaurantepos.DTOs.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO dto);
    OrderResponseDTO findById(Long id);
    List<OrderResponseDTO> listAll();
    OrderResponseDTO updateStatus(Long id, String status);
    void deleteOrder(Long id);

}
