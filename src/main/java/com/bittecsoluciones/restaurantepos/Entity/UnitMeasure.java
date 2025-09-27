package com.bittecsoluciones.restaurantepos.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "Unit_Measure")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UnitMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String abbreviation;

    private Boolean active = true;

    @OneToMany(mappedBy = "unitMeasure", fetch = FetchType.LAZY)
    private Set<Ingredient> ingredients;
}