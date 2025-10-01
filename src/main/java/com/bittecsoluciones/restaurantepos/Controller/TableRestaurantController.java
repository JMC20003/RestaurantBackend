package com.bittecsoluciones.restaurantepos.Controller;

import com.bittecsoluciones.restaurantepos.Entity.TableRestaurant;
import com.bittecsoluciones.restaurantepos.Exceptions.ResourceNotFoundException;
import com.bittecsoluciones.restaurantepos.Service.TableRestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/tableRestaurant")
public class TableRestaurantController {
    private final TableRestaurantService tableRestaurantService;

    public TableRestaurantController(TableRestaurantService tableRestaurantService) {
        this.tableRestaurantService = tableRestaurantService;
    }

    // Crear nueva categoria
    @PostMapping("/create")
    public ResponseEntity<TableRestaurant> createTableRestaurant(@Valid @RequestBody TableRestaurant tableRestaurant) {
        TableRestaurant created = tableRestaurantService.createTableRestaurant(tableRestaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Obtener todas las categorias
    @GetMapping("/list")
    public ResponseEntity<List<TableRestaurant>> getAllTableRestaurants() {
        return ResponseEntity.ok(tableRestaurantService.getAllTableRestaurants());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<TableRestaurant> getTableRestaurantById(@PathVariable Long id) {
        TableRestaurant tableRestaurant = tableRestaurantService.getTableRestaurantById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor con id " + id + " no encontrada"));
        return ResponseEntity.ok(tableRestaurant);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<TableRestaurant> updateTableRestaurant(
            @PathVariable Long id,
            @Valid @RequestBody TableRestaurant tableRestaurant) {

        TableRestaurant updated = tableRestaurantService.updateTableRestaurant(id, tableRestaurant);
        return ResponseEntity.ok(updated);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTableRestaurant(@PathVariable Long id) {
        tableRestaurantService.deleteTableRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}
