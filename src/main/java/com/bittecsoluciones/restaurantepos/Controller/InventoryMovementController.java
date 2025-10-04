package com.bittecsoluciones.restaurantepos.Controller;

import com.bittecsoluciones.restaurantepos.DTOs.InventoryMovementRequestDTO;
import com.bittecsoluciones.restaurantepos.DTOs.InventoryMovementResponseDTO;
import com.bittecsoluciones.restaurantepos.Service.InventoryMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-movement")
@RequiredArgsConstructor
public class InventoryMovementController {

    private final InventoryMovementService movementService;

    @GetMapping("/list")
    public ResponseEntity<List<InventoryMovementResponseDTO>> getAllMovements() {
        return ResponseEntity.ok(movementService.getAllInventoryMovements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryMovementResponseDTO> getMovementById(@PathVariable Long id) {
        return ResponseEntity.ok(movementService.getInventoryMovementById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<InventoryMovementResponseDTO> createMovement(@RequestBody InventoryMovementRequestDTO dto) {
        return ResponseEntity.ok(movementService.createInventoryMovement(dto));
    }
    //no se usa
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovement(@PathVariable Long id) {
        movementService.deleteInventoryMovement(id);
        return ResponseEntity.ok("Movimiento eliminado con éxito");
    }
}

