package com.bittecsoluciones.restaurantepos.Repository;

import com.bittecsoluciones.restaurantepos.Entity.ProductIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, Long> {
    List<ProductIngredient> findByProductId(Long productId);
}
