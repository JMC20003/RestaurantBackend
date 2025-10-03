package com.bittecsoluciones.restaurantepos.Service;


import com.bittecsoluciones.restaurantepos.DTOs.IngredientRequestDTO;
import com.bittecsoluciones.restaurantepos.DTOs.IngredientResponseDTO;



import java.util.List;
import java.util.Optional;

public interface IngredientService {
    List<IngredientResponseDTO> getAllIngredients();
    IngredientResponseDTO getIngredientById(Long id);
    IngredientResponseDTO createIngredient(IngredientRequestDTO ingredientRequest);
    IngredientResponseDTO updateIngredient(Long id, IngredientRequestDTO ingredientRequest);
    void deleteIngredient(Long id);

    //REPORTES
    Optional<IngredientResponseDTO> getIngredientByName(String name);
}
