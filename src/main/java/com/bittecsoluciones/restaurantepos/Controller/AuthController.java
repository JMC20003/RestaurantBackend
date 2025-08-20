package com.bittecsoluciones.restaurantepos.Controller;

import com.bittecsoluciones.restaurantepos.DTOs.AdminCreateUserRequest;
import com.bittecsoluciones.restaurantepos.DTOs.JwtResponse;
import com.bittecsoluciones.restaurantepos.DTOs.LoginRequest;
import com.bittecsoluciones.restaurantepos.DTOs.RegisterRequest;
import com.bittecsoluciones.restaurantepos.Entity.Role;
import com.bittecsoluciones.restaurantepos.Entity.User;
import com.bittecsoluciones.restaurantepos.Entity.UserRole;
import com.bittecsoluciones.restaurantepos.Entity.UserRoleId;
import com.bittecsoluciones.restaurantepos.Repository.RoleRepository;
import com.bittecsoluciones.restaurantepos.Repository.UserRepository;
import com.bittecsoluciones.restaurantepos.Security.JwtUtils;
import com.bittecsoluciones.restaurantepos.ServiceImpl.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userPrincipal);

        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity.ok(new JwtResponse(jwt, userPrincipal.getUsername(), roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username ya está en uso");
        }

        // 1. Buscar rol CUSTOMER (si no existe, créalo)
        Role roleCustomer = roleRepository.findByName("CUSTOMER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("CUSTOMER");
                    newRole.setActive(true);
                    newRole.setCreatedAt(LocalDateTime.now());
                    return roleRepository.save(newRole);
                });

        // 2. Crear el usuario
        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .name(registerRequest.getName())
                .lastname(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .phone(registerRequest.getPhone())
                .address(registerRequest.getAddress())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        // 3. Guardar el usuario primero para generar el ID
        User savedUser = userRepository.save(user);

        // 4. Crear la relación UserRole
        UserRole userRole = new UserRole(savedUser, roleCustomer);

        // 5. Asignar la relación con un HashSet mutable
        Set<UserRole> roles = new HashSet<>();
        roles.add(userRole);
        savedUser.setUserRoles(roles);

        // 6. Guardar de nuevo con el rol asignado
        userRepository.save(savedUser);

        return ResponseEntity.ok("Usuario registrado exitosamente con rol CUSTOMER");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/create-user")
    public ResponseEntity<?> createUserByAdmin(@RequestBody AdminCreateUserRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username ya está en uso");
        }

        // Crear usuario base
        User user = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName())
                .lastname(req.getLastname())
                .email(req.getEmail())
                .phone(req.getPhone())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        // Guardamos primero para generar el ID
        userRepository.save(user);

        Set<UserRole> roles = new HashSet<>();

        for (String roleName : req.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName(roleName);
                        newRole.setActive(true);
                        newRole.setCreatedAt(LocalDateTime.now());
                        return roleRepository.save(newRole);
                    });

            // Crear el ID compuesto
            UserRoleId userRoleId = new UserRoleId(role.getId(), user.getId());

            UserRole userRole = new UserRole();
            userRole.setId(userRoleId);
            userRole.setUser(user);
            userRole.setRole(role);
            userRole.setAssignedAt(LocalDateTime.now());

            roles.add(userRole);
        }

        // Asociamos roles al usuario
        user.setUserRoles(roles);

        // Guardamos otra vez (ahora con roles)
        userRepository.save(user);

        return ResponseEntity.ok("Usuario creado exitosamente con roles: " + req.getRoles());
    }

}