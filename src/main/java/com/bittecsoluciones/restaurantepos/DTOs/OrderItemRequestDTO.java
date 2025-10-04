package com.bittecsoluciones.restaurantepos.DTOs;

import java.math.BigDecimal;

public record OrderItemRequestDTO(
        Long productId,
        BigDecimal quantity,
        BigDecimal unitPrice,
        String notes
) {}