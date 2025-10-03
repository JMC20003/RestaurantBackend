package com.bittecsoluciones.restaurantepos.DTOs;

import com.bittecsoluciones.restaurantepos.Entity.Purchase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PurchaseResponseDTO(
        Long id,
        Long supplierId,
        String supplierName,
        String notes,
        BigDecimal total,
        LocalDateTime purchaseDate,
        Long createdById,
        String createdByName,
        List<PurchaseDetailDTO> details
) {
    public static PurchaseResponseDTO from(Purchase purchase) {
        return new PurchaseResponseDTO(
                purchase.getId(),
                purchase.getSupplier() != null ? purchase.getSupplier().getId() : null,
                purchase.getSupplier() != null ? purchase.getSupplier().getName() : null,
                purchase.getNotes(),
                purchase.getTotal(),
                purchase.getPurchaseDate(),
                purchase.getCreatedBy() != null ? purchase.getCreatedBy().getId() : null,
                purchase.getCreatedBy() != null ? purchase.getCreatedBy().getName() : null,
                purchase.getDetails()
                        .stream()
                        .map(PurchaseDetailDTO::from)
                        .toList()

        );
    }
}