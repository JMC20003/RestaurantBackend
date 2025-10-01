package com.bittecsoluciones.restaurantepos.Repository;

import com.bittecsoluciones.restaurantepos.Entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}
