package com.bittecsoluciones.restaurantepos.DTOs;

import java.math.BigDecimal;

public record InventoryMovementRequestDTO(
        Long ingredientId,
        String movementType, // "Entrada" o "Salida"
        BigDecimal quantity,
        BigDecimal unitCost,
        String referenceType,
        Integer referenceId,
        String notes,
        Long userId
) {}