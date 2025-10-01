package com.bittecsoluciones.restaurantepos.Controller;

import com.bittecsoluciones.restaurantepos.Entity.AreaAttention;
import com.bittecsoluciones.restaurantepos.Exceptions.ResourceNotFoundException;
import com.bittecsoluciones.restaurantepos.Service.AreaAttentionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/areaAttention")
public class AreaAttentionController {
    private final AreaAttentionService areaAttentionService;

    public AreaAttentionController(AreaAttentionService areaAttentionService) {
        this.areaAttentionService = areaAttentionService;
    }

    // Crear nueva categoria
    @PostMapping("/create")
    public ResponseEntity<AreaAttention> createAreaAttention(@Valid @RequestBody AreaAttention areaAttention) {
        AreaAttention created = areaAttentionService.createAreaAttention(areaAttention);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Obtener todas las categorias
    @GetMapping("/list")
    public ResponseEntity<List<AreaAttention>> getAllAreaAttentions() {
        return ResponseEntity.ok(areaAttentionService.getAllAreaAttentions());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<AreaAttention> getAreaAttentionById(@PathVariable Long id) {
        AreaAttention areaAttention = areaAttentionService.getAreaAttentionById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor con id " + id + " no encontrada"));
        return ResponseEntity.ok(areaAttention);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<AreaAttention> updateAreaAttention(
            @PathVariable Long id,
            @Valid @RequestBody AreaAttention areaAttention) {

        AreaAttention updated = areaAttentionService.updateAreaAttention(id, areaAttention);
        return ResponseEntity.ok(updated);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAreaAttention(@PathVariable Long id) {
        areaAttentionService.deleteAreaAttention(id);
        return ResponseEntity.noContent().build();
    }
}