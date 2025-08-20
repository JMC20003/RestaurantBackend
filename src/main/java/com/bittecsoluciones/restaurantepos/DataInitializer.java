package com.bittecsoluciones.restaurantepos;

import com.bittecsoluciones.restaurantepos.Entity.Role;
import com.bittecsoluciones.restaurantepos.Entity.User;
import com.bittecsoluciones.restaurantepos.Entity.UserRole;
import com.bittecsoluciones.restaurantepos.Entity.UserRoleId;
import com.bittecsoluciones.restaurantepos.Repository.RoleRepository;
import com.bittecsoluciones.restaurantepos.Repository.UserRepository;
import com.bittecsoluciones.restaurantepos.Repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() ->
                roleRepository.save(Role.builder()
                        .name("ADMIN")
                        .description("Rol administrador")
                        .active(true)
                        .createdAt(LocalDateTime.now())
                        .build())
        );

        Role customerRole = roleRepository.findByName("CUSTOMER").orElseGet(() ->
                roleRepository.save(Role.builder()
                        .name("CUSTOMER")
                        .description("Rol cliente")
                        .active(true)
                        .createdAt(LocalDateTime.now())
                        .build())
        );

        if (!userRepository.existsByUsername("admin")) {
            // Crear usuario
            User adminUser = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .name("Admin")
                    .lastname("System")
                    .email("admin@restaurante.com")
                    .phone("999999999")
                    .active(true)
                    .createdAt(LocalDateTime.now())
                    .build();

            adminUser = userRepository.save(adminUser);

            // Crear la relación con el nuevo constructor
            UserRole userRole = new UserRole(adminUser, adminRole);

            userRoleRepository.save(userRole);

            System.out.println("✅ Usuario admin creado con rol ADMIN");
        }
    }
}