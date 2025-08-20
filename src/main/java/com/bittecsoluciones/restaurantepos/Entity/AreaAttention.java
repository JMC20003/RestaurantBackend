package com.bittecsoluciones.restaurantepos.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "Area_Attention")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaAttention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "areaAttention", fetch = FetchType.LAZY)
    private Set<Order> orders;
}