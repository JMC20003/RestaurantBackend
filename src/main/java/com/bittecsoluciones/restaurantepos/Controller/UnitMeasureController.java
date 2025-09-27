package com.bittecsoluciones.restaurantepos.Controller;


import com.bittecsoluciones.restaurantepos.Entity.UnitMeasure;
import com.bittecsoluciones.restaurantepos.Exceptions.ResourceNotFoundException;
import com.bittecsoluciones.restaurantepos.Service.UnitMeasureService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/unit-measure")
public class UnitMeasureController {
    private final UnitMeasureService unitMeasureService;

    public UnitMeasureController(UnitMeasureService unitMeasureService) {
        this.unitMeasureService = unitMeasureService;
    }

    // Crear nueva unidad de medida
    @PostMapping("/create")
    public ResponseEntity<UnitMeasure> createUnitMeasure(@Valid @RequestBody UnitMeasure unitMeasure) {
        UnitMeasure created = unitMeasureService.createUnitMeasure(unitMeasure);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Obtener todas las unidades de medida
    @GetMapping("/list")
    public ResponseEntity<List<UnitMeasure>> getAllUnitMeasures() {
        return ResponseEntity.ok(unitMeasureService.getAllUnitMeasures());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<UnitMeasure> getUnitMeasureById(@PathVariable Long id) {
        UnitMeasure unitMeasure = unitMeasureService.getUnitMeasureById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidad de medida con id " + id + " no encontrada"));
        return ResponseEntity.ok(unitMeasure);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<UnitMeasure> updateUnitMeasure(
            @PathVariable Long id,
            @Valid @RequestBody UnitMeasure unitMeasure) {

        UnitMeasure updated = unitMeasureService.updateUnitMeasure(id, unitMeasure);
        return ResponseEntity.ok(updated);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnitMeasure(@PathVariable Long id) {
        unitMeasureService.deleteUnitMeasure(id);
        return ResponseEntity.noContent().build();
    }

}
