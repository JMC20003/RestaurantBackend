package com.bittecsoluciones.restaurantepos.Controller;

import com.bittecsoluciones.restaurantepos.Entity.PaymentMethod;
import com.bittecsoluciones.restaurantepos.Exceptions.ResourceNotFoundException;
import com.bittecsoluciones.restaurantepos.Service.PaymentMethodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/paymentMethod")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    // Crear nueva categoria
    @PostMapping("/create")
    public ResponseEntity<PaymentMethod> createPaymentMethod(@Valid @RequestBody PaymentMethod paymentMethod) {
        PaymentMethod created = paymentMethodService.createPaymentMethod(paymentMethod);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Obtener todas las categorias
    @GetMapping("/list")
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethods() {
        return ResponseEntity.ok(paymentMethodService.getAllPaymentMethods());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethod> getPaymentMethodById(@PathVariable Long id) {
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor con id " + id + " no encontrada"));
        return ResponseEntity.ok(paymentMethod);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethod> updatePaymentMethod(
            @PathVariable Long id,
            @Valid @RequestBody PaymentMethod paymentMethod) {

        PaymentMethod updated = paymentMethodService.updatePaymentMethod(id, paymentMethod);
        return ResponseEntity.ok(updated);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Long id) {
        paymentMethodService.deletePaymentMethod(id);
        return ResponseEntity.noContent().build();
    }
}