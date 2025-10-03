package com.bittecsoluciones.restaurantepos.Controller;

import com.bittecsoluciones.restaurantepos.DTOs.ProductIngredientRequestDTO;
import com.bittecsoluciones.restaurantepos.DTOs.ProductIngredientResponseDTO;
import com.bittecsoluciones.restaurantepos.Service.ProductIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-ingredient")
@RequiredArgsConstructor
public class ProductIngredientController {

    private final ProductIngredientService productIngredientService;

    @GetMapping("/list")
    public List<ProductIngredientResponseDTO> getAll() {
        return productIngredientService.getAllProductIngredients();
    }

    @GetMapping("/{id}")
    public ProductIngredientResponseDTO getById(@PathVariable Long id) {
        return productIngredientService.getProductIngredientById(id);
    }

    @PostMapping("/create")
    public ProductIngredientResponseDTO create(@RequestBody ProductIngredientRequestDTO dto) {
        return productIngredientService.createProductIngredient(dto);
    }

    @PutMapping("/{id}")
    public ProductIngredientResponseDTO update(@PathVariable Long id, @RequestBody ProductIngredientRequestDTO dto) {
        return productIngredientService.updateProductIngredient(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productIngredientService.deleteProductIngredient(id);
    }
}

