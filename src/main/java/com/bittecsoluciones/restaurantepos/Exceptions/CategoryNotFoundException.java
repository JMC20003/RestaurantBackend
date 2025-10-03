package com.bittecsoluciones.restaurantepos.Exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super("Categoría con id " + id + " no encontrada");
    }
}
