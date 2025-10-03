package com.bittecsoluciones.restaurantepos.Service;

import com.bittecsoluciones.restaurantepos.DTOs.ProductIngredientRequestDTO;
import com.bittecsoluciones.restaurantepos.DTOs.ProductIngredientResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ProductIngredientService {
    List<ProductIngredientResponseDTO> getAllProductIngredients();
    ProductIngredientResponseDTO getProductIngredientById(Long id);
    ProductIngredientResponseDTO createProductIngredient(ProductIngredientRequestDTO productIngredientRequestDTO);
    ProductIngredientResponseDTO updateProductIngredient(Long id, ProductIngredientRequestDTO productIngredientRequestDTO);
    void deleteProductIngredient(Long id);
    //REPORTES

}
