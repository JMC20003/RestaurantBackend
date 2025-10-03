package com.bittecsoluciones.restaurantepos.Service;

import com.bittecsoluciones.restaurantepos.DTOs.InventoryMovementRequestDTO;
import com.bittecsoluciones.restaurantepos.DTOs.InventoryMovementResponseDTO;

import java.util.List;

public interface InventoryMovementService {
    //CRUD
    List<InventoryMovementResponseDTO> getAllInventoryMovements();
    InventoryMovementResponseDTO getInventoryMovementById(Long id);
    InventoryMovementResponseDTO createInventoryMovement(InventoryMovementRequestDTO inventoryMovementRequestDTO);
    InventoryMovementResponseDTO updateInventoryMovement(Long id, InventoryMovementRequestDTO inventoryMovementRequestDTO);
    void deleteInventoryMovement(Long id);
}
