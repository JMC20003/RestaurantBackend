package com.bittecsoluciones.restaurantepos.ServiceImpl;

import com.bittecsoluciones.restaurantepos.DTOs.ProductIngredientRequestDTO;
import com.bittecsoluciones.restaurantepos.DTOs.ProductIngredientResponseDTO;
import com.bittecsoluciones.restaurantepos.Entity.ProductIngredient;
import com.bittecsoluciones.restaurantepos.Repository.IngredientRepository;
import com.bittecsoluciones.restaurantepos.Repository.ProductIngredientRepository;
import com.bittecsoluciones.restaurantepos.Repository.ProductRepository;
import com.bittecsoluciones.restaurantepos.Service.ProductIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductIngredientServiceImpl implements ProductIngredientService {

    @Autowired
    private ProductIngredientRepository productIngredientRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private IngredientRepository ingredientRepository;


    @Override
    public List<ProductIngredientResponseDTO> getAllProductIngredients() {
        return productIngredientRepository.findAll()
                .stream()
                .map(ProductIngredientResponseDTO::from)
                .toList();
    }

    @Override
    public ProductIngredientResponseDTO getProductIngredientById(Long id) {
        var pi = productIngredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asociación Producto-Ingrediente no encontrada"));
        return ProductIngredientResponseDTO.from(pi);
    }

    @Override
    public ProductIngredientResponseDTO createProductIngredient(ProductIngredientRequestDTO productIngredientRequestDTO) {
        var product = productRepository.findById(productIngredientRequestDTO.productId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        var ingredient = ingredientRepository.findById(productIngredientRequestDTO.ingredientId())
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

        var pi = ProductIngredient.builder()
                .product(product)
                .ingredient(ingredient)
                .quantityRequired(productIngredientRequestDTO.quantity())
                .build();

        return ProductIngredientResponseDTO.from(productIngredientRepository.save(pi));
    }

    @Override
    public ProductIngredientResponseDTO updateProductIngredient(Long id, ProductIngredientRequestDTO productIngredientRequestDTO) {
        var pi = productIngredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asociación Producto-Ingrediente no encontrada"));

        var product = productRepository.findById(productIngredientRequestDTO.productId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        var ingredient = ingredientRepository.findById(productIngredientRequestDTO.ingredientId())
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

        pi.setProduct(product);
        pi.setIngredient(ingredient);
        pi.setQuantityRequired(productIngredientRequestDTO.quantity());

        return ProductIngredientResponseDTO.from(productIngredientRepository.save(pi));
    }

    @Override
    public void deleteProductIngredient(Long id) {
        if (!productIngredientRepository.existsById(id)) {
            throw new RuntimeException("Asociación Producto-Ingrediente no encontrada");
        }
        productIngredientRepository.deleteById(id);
    }

}
