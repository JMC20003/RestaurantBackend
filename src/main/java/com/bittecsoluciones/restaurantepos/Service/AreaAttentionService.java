package com.bittecsoluciones.restaurantepos.Service;

import com.bittecsoluciones.restaurantepos.Entity.AreaAttention;

import java.util.List;
import java.util.Optional;

public interface AreaAttentionService {
    List<AreaAttention> getAllAreaAttentions();
    Optional<AreaAttention> getAreaAttentionById(Long id);
    AreaAttention createAreaAttention(AreaAttention areaAttention);
    AreaAttention updateAreaAttention(Long id, AreaAttention areaAttention);
    void deleteAreaAttention(Long id);
}
