package com.bittecsoluciones.restaurantepos.Service;

import com.bittecsoluciones.restaurantepos.DTOs.PurchaseRequestDTO;
import com.bittecsoluciones.restaurantepos.DTOs.PurchaseResponseDTO;

import java.util.List;

public interface PurchaseService {
    List<PurchaseResponseDTO> getAllPurchases();
    PurchaseResponseDTO getPurchaseById(Long id);
    PurchaseResponseDTO createPurchase(PurchaseRequestDTO dto);
    PurchaseResponseDTO updatePurchase(Long id, PurchaseRequestDTO dto);
    void deletePurchase(Long id);
}
