package com.bittecsoluciones.restaurantepos.Controller;

import com.bittecsoluciones.restaurantepos.DTOs.OrderItemResponseDTO;
import com.bittecsoluciones.restaurantepos.Service.OrderItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.findById(id));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItemResponseDTO>> listByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderItemService.listByOrder(orderId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderItemResponseDTO> updateStatus(@PathVariable Long id,
                                                             @RequestParam String status) {
        return ResponseEntity.ok(orderItemService.updateStatus(id, status));
    }

    @PutMapping("/{id}/assign-chef")
    public ResponseEntity<OrderItemResponseDTO> assignChef(@PathVariable Long id,
                                                           @RequestParam Long chefId) {
        return ResponseEntity.ok(orderItemService.assignChef(id, chefId));
    }
}
