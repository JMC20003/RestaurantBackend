package com.bittecsoluciones.restaurantepos.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Ingredients")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "cost_per_unit", nullable = false, precision = 10, scale = 2)
    private BigDecimal costPerUnit;

    @Column(name = "minimum_stock", nullable = false, precision = 10, scale = 3)
    private BigDecimal minimumStock;

    @Column(name = "current_stock", nullable = false, precision = 10, scale = 3)
    private BigDecimal currentStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_measure_id", nullable = false)
    private UnitMeasure unitMeasure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    private Boolean active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
    private Set<ProductIngredient> productIngredients;

    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
    private Set<InventoryMovement> inventoryMovements;
}