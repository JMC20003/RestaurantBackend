package com.bittecsoluciones.restaurantepos.DTOs;

import com.bittecsoluciones.restaurantepos.Entity.OrderItem;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponseDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private String notes;
    private String status;
    private Long chefId;
    private LocalDateTime preparedAt;
    private LocalDateTime servedAt;

    public static OrderItemResponseDTO from(OrderItem item) {
        return OrderItemResponseDTO.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .subtotal(item.getSubtotal())
                .notes(item.getNotes())
                .status(item.getStatus())
                .chefId(item.getChef() != null ? item.getChef().getId() : null)
                .preparedAt(item.getPreparedAt())
                .servedAt(item.getServedAt())
                .build();
    }
}
