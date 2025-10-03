package com.bittecsoluciones.restaurantepos.DTOs;

import java.util.List;

public record PurchaseRequestDTO(
        Long supplier,
        String notes,
        Long userId,
        List<PurchaseDetailRequestDTO> details
) {}
