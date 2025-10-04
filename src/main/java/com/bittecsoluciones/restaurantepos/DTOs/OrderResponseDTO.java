package com.bittecsoluciones.restaurantepos.DTOs;

import com.bittecsoluciones.restaurantepos.Entity.Order;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private String orderNumber;
    private Long tableId;
    private Long customerId;
    private Long waiterId;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private BigDecimal total;
    private String notes;
    private String status;
    private LocalDateTime orderDate;
    private LocalDateTime servedAt;
    private LocalDateTime closedAt;
    private Long areaAttentionId;
    private List<OrderItemResponseDTO> items;

    public static OrderResponseDTO from(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .tableId(order.getTable() != null ? order.getTable().getId() : null)
                .customerId(order.getCustomer() != null ? order.getCustomer().getId() : null)
                .waiterId(order.getWaiter() != null ? order.getWaiter().getId() : null)
                .subtotal(order.getSubtotal())
                .taxAmount(order.getTaxAmount())
                .discountAmount(order.getDiscountAmount())
                .total(order.getTotal())
                .notes(order.getNotes())
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .servedAt(order.getServedAt())
                .closedAt(order.getClosedAt())
                .areaAttentionId(order.getAreaAttention().getId())
                .items(order.getOrderItems().stream()
                        .map(OrderItemResponseDTO::from)
                        .toList())
                .build();
    }
}

