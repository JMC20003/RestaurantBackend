package com.bittecsoluciones.restaurantepos.ServiceImpl;

import com.bittecsoluciones.restaurantepos.DTOs.ProductRequestDTO;
import com.bittecsoluciones.restaurantepos.DTOs.ProductResponseDTO;
import com.bittecsoluciones.restaurantepos.Entity.Product;
import com.bittecsoluciones.restaurantepos.Repository.CategoryRepository;
import com.bittecsoluciones.restaurantepos.Repository.ProductRepository;
import com.bittecsoluciones.restaurantepos.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDTO) // usamos el mapper
                .toList();
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return mapToDTO(product);
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        var category = categoryRepository.findById(productRequestDTO.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        var product = Product.builder()
                .sku(productRequestDTO.sku())
                .name(productRequestDTO.name())
                .description(productRequestDTO.description())
                .cost(productRequestDTO.cost())
                .price(productRequestDTO.price())
                .image(productRequestDTO.image())
                .active(productRequestDTO.active())
                .preparationTime(productRequestDTO.preparationTime())
                .category(category)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        var saved = productRepository.save(product);
        return mapToDTO(saved);
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        var category = categoryRepository.findById(productRequestDTO.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        product.setSku(productRequestDTO.sku());
        product.setName(productRequestDTO.name());
        product.setDescription(productRequestDTO.description());
        product.setCost(productRequestDTO.cost());
        product.setPrice(productRequestDTO.price());
        product.setImage(productRequestDTO.image());
        product.setActive(productRequestDTO.active());
        product.setPreparationTime(productRequestDTO.preparationTime());
        product.setCategory(category);
        product.setUpdatedAt(LocalDateTime.now());

        var updated = productRepository.save(product);
        return mapToDTO(updated);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productRepository.deleteById(id);
    }

    @Override
    public Optional<ProductResponseDTO> getProductByName(String name) {
        return productRepository.findByNameContaining(name).map(this::mapToDTO);
    }

    // Mapper privado
    private ProductResponseDTO mapToDTO(Product product) {
        return ProductResponseDTO.from(product);
    }
}
