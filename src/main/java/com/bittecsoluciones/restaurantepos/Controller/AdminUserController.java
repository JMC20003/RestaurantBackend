package com.bittecsoluciones.restaurantepos.Controller;

import com.bittecsoluciones.restaurantepos.DTOs.ProfileResponse;
import com.bittecsoluciones.restaurantepos.DTOs.UpdateProfileRequest;
import com.bittecsoluciones.restaurantepos.Entity.User;
import com.bittecsoluciones.restaurantepos.Repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserRepository userRepository;

    private ProfileResponse mapToProfileResponse(User user) {
        return new ProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getLastname(),
                user.getEmail(),
                user.getPhone(),
                user.getUserRoles().stream()
                        .map(ur -> ur.getRole().getName())
                        .collect(Collectors.toSet())
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProfileResponse>> getAllUsers() {
        List<ProfileResponse> users = userRepository.findAll().stream()
                .map(this::mapToProfileResponse)
                .toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfileResponse> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(mapToProfileResponse(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfileResponse> updateUserByAdmin(@PathVariable Long id,
                                                             @Valid @RequestBody UpdateProfileRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setName(req.getName());
        user.setLastname(req.getLastname());
        user.setPhone(req.getPhone());
        user.setEmail(req.getEmail());

        userRepository.save(user);

        return ResponseEntity.ok(mapToProfileResponse(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        userRepository.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado con éxito");
    }
}