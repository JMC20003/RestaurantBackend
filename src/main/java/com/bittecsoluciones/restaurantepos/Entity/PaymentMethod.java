package com.bittecsoluciones.restaurantepos.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "Payment_Method")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    private Boolean active = true;

    @OneToMany(mappedBy = "paymentMethod", fetch = FetchType.LAZY)
    private Set<Payment> payments;
}