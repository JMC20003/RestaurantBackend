package com.bittecsoluciones.restaurantepos.Controller;

import com.bittecsoluciones.restaurantepos.DTOs.ProfileResponse;
import com.bittecsoluciones.restaurantepos.DTOs.UpdateProfileRequest;
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

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserPrincipal userDetails = (UserPrincipal) auth.getPrincipal();
//
//        User user = userRepository.findByUsername(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setName(req.getName());
        user.setLastname(req.getLastname());
        user.setPhone(req.getPhone());
        user.setEmail(req.getEmail());

        userRepository.save(user);

        return ResponseEntity.ok(mapToProfileResponse(user));
    }
}
