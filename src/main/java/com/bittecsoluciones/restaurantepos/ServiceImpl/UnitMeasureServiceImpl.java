package com.bittecsoluciones.restaurantepos.ServiceImpl;

import com.bittecsoluciones.restaurantepos.Entity.UnitMeasure;
import com.bittecsoluciones.restaurantepos.Entity.User;
import com.bittecsoluciones.restaurantepos.Exceptions.ResourceNotFoundException;
import com.bittecsoluciones.restaurantepos.Repository.UnitMeasureRepository;
import com.bittecsoluciones.restaurantepos.Service.UnitMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitMeasureServiceImpl implements UnitMeasureService {

    @Autowired
    private UnitMeasureRepository unitMeasureRepository;

    @Override
    public List<UnitMeasure> getAllUnitMeasures() {
        return unitMeasureRepository.findAll();
    }

    @Override
    public Optional<UnitMeasure> getUnitMeasureById(Long id) {
        return unitMeasureRepository.findById(id);
    }

    @Override
    public UnitMeasure createUnitMeasure(UnitMeasure unitMeasure) {
        return unitMeasureRepository.save(unitMeasure);
    }

    @Override
    public UnitMeasure updateUnitMeasure(Long id, UnitMeasure unitMeasure) {
        // Verificar que exista
        UnitMeasure existing = unitMeasureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidad de medida con id " + id + " no encontrada"));

        // Actualizar los campos necesarios
        existing.setName(unitMeasure.getName());
        existing.setAbbreviation(unitMeasure.getAbbreviation() ) ;
        existing.setActive(unitMeasure.getActive());

        return unitMeasureRepository.save(existing);
    }

    @Override
    public void deleteUnitMeasure(Long id) {
        unitMeasureRepository.deleteById(id);
    }

    @Override
    public Optional<UnitMeasure> findByName(String name) {
        return unitMeasureRepository.findByName(name);
    }
}
