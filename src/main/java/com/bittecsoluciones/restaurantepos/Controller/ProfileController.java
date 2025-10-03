package com.bittecsoluciones.restaurantepos.Controller;

import com.bittecsoluciones.restaurantepos.DTOs.Auth.ProfileResponse;
import com.bittecsoluciones.restaurantepos.DTOs.Auth.UpdateProfileRequest;
import com.bittecsoluciones.restaurantepos.Entity.Customer;
import com.bittecsoluciones.restaurantepos.Entity.User;
import com.bittecsoluciones.restaurantepos.Repository.UserRepository;
import com.bittecsoluciones.restaurantepos.ServiceImpl.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserRepository userRepository;

    private ProfileResponse mapToProfileResponse(User user) {
        Customer customer = user.getCustomer();
        return new ProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getLastname(),
                user.getEmail(),
                user.getPhone(),
                user.getUserRoles().stream()
                        .map(ur -> ur.getRole().getName())
                        .collect(Collectors.toSet()),
                customer != null ? customer.getBirthDate() : null,
                customer != null ? customer.getLoyaltyPoints() : 0
        );
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getMyProfile(Authentication authentication) {
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserPrincipal userDetails = (UserPrincipal) auth.getPrincipal();
//
//        User user = userRepository.findByUsername(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(mapToProfileResponse(user));
    }

    @PutMapping("/me")
    public ResponseEntity<ProfileResponse> updateMyProfile(@Valid @RequestBody UpdateProfileRequest req,
                                                           Authentication authentication) {
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Editar datos del User
        user.setName(req.getName());
        user.setLastname(req.getLastname());
        user.setPhone(req.getPhone());
        user.setEmail(req.getEmail());

        // Editar datos del Customer asociado
        Customer customer = user.getCustomer();
        if (customer != null) {
            if (req.getBirthDate() != null) customer.setBirthDate(req.getBirthDate());
            if (req.getLoyaltyPoints() != null) customer.setLoyaltyPoints(req.getLoyaltyPoints());
        }
        userRepository.save(user);

        return ResponseEntity.ok(mapToProfileResponse(user));
    }
}
