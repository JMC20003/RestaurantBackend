package com.bittecsoluciones.restaurantepos.Controller;

import com.bittecsoluciones.restaurantepos.DTOs.EmailRequest;
import com.bittecsoluciones.restaurantepos.Entity.Supplier;
import com.bittecsoluciones.restaurantepos.Exceptions.ResourceNotFoundException;
import com.bittecsoluciones.restaurantepos.Service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/supplier")

public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    // Crear nuevo proveedor
    @PostMapping("/create")
    public ResponseEntity<Supplier> createSupplier(@Valid @RequestBody Supplier supplier) {
        Supplier created = supplierService.createSupplier(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Obtener todos los proveedores
    @GetMapping("/list")
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        Supplier supplier = supplierService.getSupplierById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor con id " + id + " no encontrada"));
        return ResponseEntity.ok(supplier);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(
            @PathVariable Long id,
            @Valid @RequestBody Supplier supplier) {

        Supplier updated = supplierService.updateSupplier(id, supplier);
        return ResponseEntity.ok(updated);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener proveedor por correo
    @PostMapping("/email")
    public ResponseEntity<Supplier> getSupplierByEmail(@RequestBody EmailRequest request) {
        var email = request.getEmail().trim(); // limpiar espacios
        System.out.println("Buscando email: [" + email + "]"); // log para depurar

        return supplierService.findByEmail(email)
                .map(supplier -> ResponseEntity.ok(supplier))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
