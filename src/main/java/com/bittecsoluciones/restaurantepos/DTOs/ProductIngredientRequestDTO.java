package com.bittecsoluciones.restaurantepos.DTOs;

import java.math.BigDecimal;

public record ProductIngredientRequestDTO(
        Long productId,
        Long ingredientId,
        BigDecimal quantity
) {}
