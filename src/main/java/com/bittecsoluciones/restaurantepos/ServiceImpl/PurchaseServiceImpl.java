package com.bittecsoluciones.restaurantepos.ServiceImpl;

import com.bittecsoluciones.restaurantepos.DTOs.PurchaseRequestDTO;
import com.bittecsoluciones.restaurantepos.DTOs.PurchaseResponseDTO;
import com.bittecsoluciones.restaurantepos.Service.PurchaseService;

import java.util.HashSet;
import java.util.List;
import com.bittecsoluciones.restaurantepos.Entity.*;
import com.bittecsoluciones.restaurantepos.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;
    private final InventoryMovementRepository inventoryMovementRepository;

    @Override
    public List<PurchaseResponseDTO> getAllPurchases() {
        return purchaseRepository.findAll()
                .stream()
                .map(PurchaseResponseDTO::from)
                .toList();
    }

    @Override
    public PurchaseResponseDTO getPurchaseById(Long id) {
        var purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        return PurchaseResponseDTO.from(purchase);
    }

    @Override
    public PurchaseResponseDTO createPurchase(PurchaseRequestDTO dto) {
        var supplier = supplierRepository.findById(dto.supplier())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        var user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        var purchase = Purchase.builder()
                .supplier(supplier)
                .createdBy(user)
                .purchaseDate(LocalDateTime.now())
                .notes(dto.notes())
                .total(BigDecimal.ZERO) // inicial, lo calculamos
                .build();

        BigDecimal total = BigDecimal.ZERO;



        // crear detalles
        for (var detailDto : dto.details()) {
            var ingredient = ingredientRepository.findById(detailDto.ingredientId())
                    .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

            var subtotal = detailDto.unitCost().multiply(detailDto.quantity());
            total = total.add(subtotal);

            var detail = PurchaseDetail.builder()
                    .purchase(purchase)
                    .ingredient(ingredient)
                    .quantity(detailDto.quantity())
                    .unitCost(detailDto.unitCost())
                    .subtotal(subtotal)
                    .build();

            if (purchase.getDetails() == null) {
                purchase.setDetails(new HashSet<>());
            }
            purchase.getDetails().add(detail);
        }

        purchase.setTotal(total);
        var saved = purchaseRepository.save(purchase);
        // ⚡ Ahora creamos los movimientos de inventario
        for (PurchaseDetail detail : saved.getDetails()) {
            InventoryMovement movement = InventoryMovement.builder()
                    .ingredient(detail.getIngredient())
                    .movementType("Entrada")
                    .quantity(detail.getQuantity())
                    .unitCost(detail.getUnitCost())
                    .referenceType("Purchase")
                    .referenceId(saved.getId().intValue()) // relación con la compra
                    .notes("Compra registrada: " + saved.getNotes())
                    .user(saved.getCreatedBy())
                    .movementDate(LocalDateTime.now())
                    .build();

            inventoryMovementRepository.save(movement);
        }


        return PurchaseResponseDTO.from(saved);
    }

    @Override
    public PurchaseResponseDTO updatePurchase(Long id, PurchaseRequestDTO dto) {
        var purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        var supplier = supplierRepository.findById(dto.supplier())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        var user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        purchase.setSupplier(supplier);
        purchase.setCreatedBy(user);
        purchase.setNotes(dto.notes());
        purchase.setPurchaseDate(LocalDateTime.now());

        purchase.getDetails().clear();
        BigDecimal total = BigDecimal.ZERO;

        for (var detailDto : dto.details()) {
            var ingredient = ingredientRepository.findById(detailDto.ingredientId())
                    .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

            var subtotal = detailDto.unitCost().multiply(detailDto.quantity());
            total = total.add(subtotal);

            var detail = PurchaseDetail.builder()
                    .purchase(purchase)
                    .ingredient(ingredient)
                    .quantity(detailDto.quantity())
                    .unitCost(detailDto.unitCost())
                    .subtotal(subtotal)
                    .build();

            purchase.getDetails().add(detail);
        }

        purchase.setTotal(total);

        var updated = purchaseRepository.save(purchase);
        return PurchaseResponseDTO.from(updated);
    }

    @Override
    public void deletePurchase(Long id) {
        if (!purchaseRepository.existsById(id)) {
            throw new RuntimeException("Compra no encontrada");
        }
        purchaseRepository.deleteById(id);
    }
}
