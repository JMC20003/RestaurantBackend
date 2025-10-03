package com.bittecsoluciones.restaurantepos.DTOs;


import com.bittecsoluciones.restaurantepos.Entity.PurchaseDetail;

import java.math.BigDecimal;

public record PurchaseDetailDTO(
        Long id,
        Long ingredientId,
        String ingredientName,
        BigDecimal quantity,
        BigDecimal unitCost,
        BigDecimal subtotal,
        String unitMeasureName
) {
    public static PurchaseDetailDTO from(PurchaseDetail d) {
        return new PurchaseDetailDTO(
                d.getId(),
                d.getIngredient().getId(),
                d.getIngredient().getName(),
                d.getQuantity(),
                d.getUnitCost(),
                d.getSubtotal(),
                d.getIngredient().getUnitMeasure() != null
                        ? d.getIngredient().getUnitMeasure().getName()
                        : null // aquí asignamos la unidad
        );
    }
}
