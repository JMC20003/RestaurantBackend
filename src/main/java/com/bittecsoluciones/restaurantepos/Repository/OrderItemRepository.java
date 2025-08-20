package com.bittecsoluciones.restaurantepos.Repository;

import com.bittecsoluciones.restaurantepos.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
