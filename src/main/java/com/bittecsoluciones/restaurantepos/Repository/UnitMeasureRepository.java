package com.bittecsoluciones.restaurantepos.Repository;

import com.bittecsoluciones.restaurantepos.Entity.UnitMeasure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitMeasureRepository extends JpaRepository<UnitMeasure, Long> {

    Optional<UnitMeasure> findByName(String name);
}
