package com.bittecsoluciones.restaurantepos.DTOs;

import com.bittecsoluciones.restaurantepos.Entity.Product;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String sku,
        String name,
        String description,
        BigDecimal cost,
        BigDecimal price,
        String image,
        Boolean active,
        Integer preparationTime,
        CategoryResponse category
) {
    public static ProductResponseDTO from(Product p) {
        return new ProductResponseDTO(
                p.getId(),
                p.getSku(),
                p.getName(),
                p.getDescription(),
                p.getCost(),
                p.getPrice(),
                p.getImage(),
                p.getActive(),
                p.getPreparationTime(),
                CategoryResponse.from(p.getCategory())
        );
    }
}
