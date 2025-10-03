package com.bittecsoluciones.restaurantepos.DTOs;

import com.bittecsoluciones.restaurantepos.Entity.Category;
import lombok.Getter;
import lombok.Setter;

public record CategoryResponse(
        Long id,
        String name,
        String description,
        String image,
        Boolean active
) {
    public static CategoryResponse from(Category c) {
        return new CategoryResponse(
                c.getId(),
                c.getName(),
                c.getDescription(),
                c.getImage(),
                c.getActive()
        );
    }
}
