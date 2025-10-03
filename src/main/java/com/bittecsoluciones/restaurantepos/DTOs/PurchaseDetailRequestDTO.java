package com.bittecsoluciones.restaurantepos.DTOs;

public record PurchaseDetailRequestDTO(
        Long ingredientId,
        java.math.BigDecimal quantity,
        java.math.BigDecimal unitCost

) {}
