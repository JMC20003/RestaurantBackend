package com.bittecsoluciones.restaurantepos.DTOs;

import java.math.BigDecimal;

public record IngredientRequestDTO(
        String name,
        String description,
        BigDecimal costPerUnit,
        BigDecimal minimumStock,
        BigDecimal currentStock,
        Long unitMeasureId,
        Long supplierId,
        Boolean active
) {}
