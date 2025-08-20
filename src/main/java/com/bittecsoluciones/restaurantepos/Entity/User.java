package com.bittecsoluciones.restaurantepos.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100)
    private String lastname;

    @Column(columnDefinition = "text")
    private String address;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    private Boolean active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relación 1 a 1 con Customer
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Customer customer;

    @OneToMany(mappedBy = "waiter", fetch = FetchType.LAZY)
    private Set<Order> takenOrders;

    @OneToMany(mappedBy = "chef", fetch = FetchType.LAZY)
    private Set<OrderItem> preparedItems;

    @OneToMany(mappedBy = "processedBy", fetch = FetchType.LAZY)
    private Set<Payment> processedPayments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<InventoryMovement> inventoryMovements;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> userRoles = new HashSet<>();
}
