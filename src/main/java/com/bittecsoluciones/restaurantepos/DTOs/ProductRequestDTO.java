package com.bittecsoluciones.restaurantepos.DTOs;

import java.math.BigDecimal;

public record ProductRequestDTO(
        String sku,
        String name,
        String description,
        BigDecimal cost,
        BigDecimal price,
        String image,
        Boolean active,
        Integer preparationTime,
        Long categoryId
) {}
