package com.bittecsoluciones.restaurantepos.ServiceImpl;

import com.bittecsoluciones.restaurantepos.DTOs.InventoryMovementRequestDTO;
import com.bittecsoluciones.restaurantepos.DTOs.InventoryMovementResponseDTO;
import com.bittecsoluciones.restaurantepos.Entity.InventoryMovement;
import com.bittecsoluciones.restaurantepos.Repository.IngredientRepository;
import com.bittecsoluciones.restaurantepos.Repository.InventoryMovementRepository;
import com.bittecsoluciones.restaurantepos.Repository.UserRepository;
import com.bittecsoluciones.restaurantepos.Service.InventoryMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryMovementImpl implements InventoryMovementService {

    @Autowired
    private InventoryMovementRepository movementRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private UserRepository userRepository;

    private InventoryMovementResponseDTO mapToDTO(InventoryMovement m) {
        return InventoryMovementResponseDTO.from(m);
    }

    @Override
    public List<InventoryMovementResponseDTO> getAllInventoryMovements() {
        return movementRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public InventoryMovementResponseDTO getInventoryMovementById(Long id) {
        var movement = movementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
        return mapToDTO(movement);
    }

    @Override
    public InventoryMovementResponseDTO createInventoryMovement(InventoryMovementRequestDTO inventoryMovementRequestDTO) {
        var ingredient = ingredientRepository.findById(inventoryMovementRequestDTO.ingredientId())
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

        var user = userRepository.findById(inventoryMovementRequestDTO.userId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        var movement = InventoryMovement.builder()
                .ingredient(ingredient)
                .movementType(inventoryMovementRequestDTO.movementType())
                .quantity(inventoryMovementRequestDTO.quantity())
                .unitCost(inventoryMovementRequestDTO.unitCost())
                .referenceType(inventoryMovementRequestDTO.referenceType())
                .referenceId(inventoryMovementRequestDTO.referenceId())
                .notes(inventoryMovementRequestDTO.notes())
                .user(user)
                .movementDate(LocalDateTime.now())
                .build();

        // Actualizar stock del ingrediente
        if ("Salida".equalsIgnoreCase(inventoryMovementRequestDTO.movementType())) {
            ingredient.setCurrentStock(ingredient.getCurrentStock().subtract(inventoryMovementRequestDTO.quantity()));
        } else if ("Entrada".equalsIgnoreCase(inventoryMovementRequestDTO.movementType())) {
            ingredient.setCurrentStock(ingredient.getCurrentStock().add(inventoryMovementRequestDTO.quantity()));
        }

        ingredientRepository.save(ingredient);

        var saved = movementRepository.save(movement);
        return mapToDTO(saved);
    }

    @Override
    public InventoryMovementResponseDTO updateInventoryMovement(Long id, InventoryMovementRequestDTO inventoryMovementRequestDTO) {
        return null;
    }

    @Override
    public void deleteInventoryMovement(Long id) {
        if (!movementRepository.existsById(id)) {
            throw new RuntimeException("Movimiento no encontrado");
        }
        movementRepository.deleteById(id);
    }
}
