package com.bittecsoluciones.restaurantepos.Service;

import com.bittecsoluciones.restaurantepos.DTOs.ProductRequestDTO;
import com.bittecsoluciones.restaurantepos.DTOs.ProductResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    //CRUD
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductById(Long id);
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO);
    void deleteProduct(Long id);
    //REPORTES
    Optional<ProductResponseDTO> getProductByName(String name);
}
