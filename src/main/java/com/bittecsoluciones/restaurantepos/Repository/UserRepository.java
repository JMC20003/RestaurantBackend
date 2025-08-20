package com.bittecsoluciones.restaurantepos.Repository;

import com.bittecsoluciones.restaurantepos.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Buscar por username
    Optional<User> findByUsername(String username);

    // Buscar por email
    Optional<User> findByEmail(String email);

    // Verificar si existe username
    boolean existsByUsername(String username);

    // Verificar si existe email
    boolean existsByEmail(String email);
}
