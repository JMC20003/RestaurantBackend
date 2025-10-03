package com.bittecsoluciones.restaurantepos.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record IngredientResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal costPerUnit,
        BigDecimal minimumStock,
        BigDecimal currentStock,
        String unitMeasureName,
        String supplierName,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
