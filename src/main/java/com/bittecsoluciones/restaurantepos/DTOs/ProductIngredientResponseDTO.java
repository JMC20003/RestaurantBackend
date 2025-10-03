package com.bittecsoluciones.restaurantepos.DTOs;

import com.bittecsoluciones.restaurantepos.Entity.ProductIngredient;

import java.math.BigDecimal;

public record ProductIngredientResponseDTO(
        Long id,
        Long productId,
        String productName,
        Long ingredientId,
        String ingredientName,
        BigDecimal quantity
) {
    public static ProductIngredientResponseDTO from(ProductIngredient pi) {
        return new ProductIngredientResponseDTO(
                pi.getId(),
                pi.getProduct().getId(),
                pi.getProduct().getName(),
                pi.getIngredient().getId(),
                pi.getIngredient().getName(),
                pi.getQuantityRequired()
        );
    }
}

