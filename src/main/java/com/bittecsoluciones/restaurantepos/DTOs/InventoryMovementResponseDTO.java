package com.bittecsoluciones.restaurantepos.DTOs;

import com.bittecsoluciones.restaurantepos.Entity.InventoryMovement;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InventoryMovementResponseDTO(
        Long id,
        String ingredientName,
        String movementType,
        BigDecimal quantity,
        BigDecimal unitCost,
        String referenceType,
        Integer referenceId,
        String notes,
        String userName,
        LocalDateTime movementDate
) {
    public static InventoryMovementResponseDTO from(InventoryMovement m) {
        return new InventoryMovementResponseDTO(
                m.getId(),
                m.getIngredient() != null ? m.getIngredient().getName() : null,
                m.getMovementType(),
                m.getQuantity(),
                m.getUnitCost(),
                m.getReferenceType(),
                m.getReferenceId(),
                m.getNotes(),
                m.getUser() != null ? m.getUser().getUsername() : null,
                m.getMovementDate()
        );
    }
}
