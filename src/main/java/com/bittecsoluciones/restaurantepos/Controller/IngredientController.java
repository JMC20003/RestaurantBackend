package com.bittecsoluciones.restaurantepos.Controller;

import com.bittecsoluciones.restaurantepos.DTOs.IngredientRequestDTO;
import com.bittecsoluciones.restaurantepos.DTOs.IngredientResponseDTO;
import com.bittecsoluciones.restaurantepos.Service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredient")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping("/create")
    public ResponseEntity<IngredientResponseDTO> create(@RequestBody IngredientRequestDTO dto) {
        return ResponseEntity.ok(ingredientService.createIngredient(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientResponseDTO> update(@PathVariable Long id, @RequestBody IngredientRequestDTO dto) {
        return ResponseEntity.ok(ingredientService.updateIngredient(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.getIngredientById(id));
    }

    @GetMapping("list")
    public ResponseEntity<List<IngredientResponseDTO>> getAll() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}