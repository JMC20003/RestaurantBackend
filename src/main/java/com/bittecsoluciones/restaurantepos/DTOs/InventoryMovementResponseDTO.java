package com.bittecsoluciones.restaurantepos.DTOs;

import com.bittecsoluciones.restaurantepos.Entity.InventoryMovement;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InventoryMovementResponseDTO(
        Long id,
        Long ingredientId,
        String ingredientName,
        String movementType,
        BigDecimal quantity,
        BigDecimal unitCost,
        String referenceType,
        Integer referenceId,
        String notes,
        Long userId,
        String userName,
        LocalDateTime movementDate
) {
    public static InventoryMovementResponseDTO from(InventoryMovement m) {
        return new InventoryMovementResponseDTO(
                m.getId(),
                m.getIngredient() != null ? m.getIngredient().getId() : null,
                m.getIngredient() != null ? m.getIngredient().getName() : null,
                m.getMovementType(),
                m.getQuantity(),
                m.getUnitCost(),
                m.getReferenceType(),
                m.getReferenceId(),
                m.getNotes(),
                m.getUser() != null ? m.getUser().getId() : null,
                m.getUser() != null ? m.getUser().getName() : null,
                m.getMovementDate()
        );
    }
}
