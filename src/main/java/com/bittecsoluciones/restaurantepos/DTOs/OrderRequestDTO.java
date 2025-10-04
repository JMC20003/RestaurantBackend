package com.bittecsoluciones.restaurantepos.DTOs;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequestDTO(
        String orderNumber,
        Long tableId,
        Long customerId,
        Long waiterId,
        BigDecimal taxAmount,
        BigDecimal discountAmount,
        String notes,
        Long areaAttentionId,
        List<OrderItemRequestDTO> items
) {}