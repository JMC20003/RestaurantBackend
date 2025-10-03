package com.bittecsoluciones.restaurantepos.Repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.bittecsoluciones.restaurantepos.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByNameContaining(String name);

}
