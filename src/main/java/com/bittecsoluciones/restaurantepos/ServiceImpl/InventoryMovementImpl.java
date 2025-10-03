package com.bittecsoluciones.restaurantepos.ServiceImpl;

import com.bittecsoluciones.restaurantepos.DTOs.InventoryMovementRequestDTO;
import com.bittecsoluciones.restaurantepos.DTOs.InventoryMovementResponseDTO;
import com.bittecsoluciones.restaurantepos.Entity.Ingredient;
import com.bittecsoluciones.restaurantepos.Entity.InventoryMovement;
import com.bittecsoluciones.restaurantepos.Entity.User;
import com.bittecsoluciones.restaurantepos.Repository.IngredientRepository;
import com.bittecsoluciones.restaurantepos.Repository.InventoryMovementRepository;
import com.bittecsoluciones.restaurantepos.Repository.UserRepository;
import com.bittecsoluciones.restaurantepos.Service.InventoryMovementService;
import jakarta.transaction.Transactional;
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


    @Override
    public List<InventoryMovementResponseDTO> getAllInventoryMovements() {
        return movementRepository.findAll().stream()
                .map(InventoryMovementResponseDTO::from)
                .toList();
    }

    @Override
    public InventoryMovementResponseDTO getInventoryMovementById(Long id) {
        var movement = movementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
        return InventoryMovementResponseDTO.from(movement);
    }

    @Override
    @Transactional
    public InventoryMovementResponseDTO createInventoryMovement(InventoryMovementRequestDTO inventoryMovementRequestDTO) {
        Ingredient ingredient = ingredientRepository.findById(inventoryMovementRequestDTO.ingredientId())
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

        User user = userRepository.findById(inventoryMovementRequestDTO.userId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // ajustar stock
        if (inventoryMovementRequestDTO.movementType().equalsIgnoreCase("Entrada")) {
            ingredient.setCurrentStock(ingredient.getCurrentStock().add(inventoryMovementRequestDTO.quantity()));
        } else if (inventoryMovementRequestDTO.movementType().equalsIgnoreCase("Salida")) {
            ingredient.setCurrentStock(ingredient.getCurrentStock().subtract(inventoryMovementRequestDTO.quantity()));
        }

        InventoryMovement movement = InventoryMovement.builder()
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

        var saved = movementRepository.save(movement);
        return InventoryMovementResponseDTO.from(saved);
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
