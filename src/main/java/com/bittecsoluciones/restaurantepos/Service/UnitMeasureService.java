package com.bittecsoluciones.restaurantepos.Service;

import com.bittecsoluciones.restaurantepos.Entity.UnitMeasure;
import com.bittecsoluciones.restaurantepos.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UnitMeasureService  {
    List<UnitMeasure> getAllUnitMeasures();
    Optional<UnitMeasure> getUnitMeasureById(Long id);
    UnitMeasure createUnitMeasure(UnitMeasure unitMeasure);
    UnitMeasure updateUnitMeasure(Long id, UnitMeasure unitMeasure);
    void deleteUnitMeasure(Long id);
    Optional<UnitMeasure> findByName(String name);
}
