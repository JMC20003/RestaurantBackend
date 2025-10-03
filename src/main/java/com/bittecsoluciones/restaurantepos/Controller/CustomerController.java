package com.bittecsoluciones.restaurantepos.Controller;

import com.bittecsoluciones.restaurantepos.DTOs.CustomerResponse;
import com.bittecsoluciones.restaurantepos.DTOs.UpdateCustomerRequest;
import com.bittecsoluciones.restaurantepos.Entity.Customer;
import com.bittecsoluciones.restaurantepos.Entity.User;
import com.bittecsoluciones.restaurantepos.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;

    // 1. Listar todos los clientes
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerRepository.findAll()
                .stream()
                .map(CustomerResponse::from) // usamos el método estático from
                .toList();

        return ResponseEntity.ok(customers);
    }

    // 2. Obtener cliente por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer no encontrado"));
        return ResponseEntity.ok(CustomerResponse.from(customer));
    }

    //editar cliente
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id,
                                                           @RequestBody UpdateCustomerRequest req) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer no encontrado"));

        // Campos de Customer
        customer.setBirthDate(req.getBirthDate());
        customer.setLoyaltyPoints(req.getLoyaltyPoints());

        // Campos del User (relación OneToOne)
        User user = customer.getUser();
        if (req.getName() != null) user.setName(req.getName());
        if (req.getLastname() != null) user.setLastname(req.getLastname());
        if (req.getEmail() != null) user.setEmail(req.getEmail());
        if (req.getPhone() != null) user.setPhone(req.getPhone());
        if (req.getAddress() != null) user.setAddress(req.getAddress());

        Customer updated = customerRepository.save(customer);
        return ResponseEntity.ok(CustomerResponse.from(updated));
    }

    // 3. Eliminar cliente
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        if (!customerRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Cliente no encontrado");
        }
        customerRepository.deleteById(id);
        return ResponseEntity.ok("Cliente eliminado con éxito");
    }
}
