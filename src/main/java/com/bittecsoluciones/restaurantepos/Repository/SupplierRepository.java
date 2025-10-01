package com.bittecsoluciones.restaurantepos.Repository;

import com.bittecsoluciones.restaurantepos.Entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByEmailIgnoreCase(String email);

}
