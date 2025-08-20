package com.bittecsoluciones.restaurantepos.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Table_Restaurant")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TableRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_number", length = 10, nullable = false)
    private String tableNumber;

    private Integer capacity;

    @Column(length = 100)
    private String location;

    private Boolean active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
    private Set<Order> orders;
}