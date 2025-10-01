package com.bittecsoluciones.restaurantepos.ServiceImpl;

import com.bittecsoluciones.restaurantepos.Entity.AreaAttention;
import com.bittecsoluciones.restaurantepos.Exceptions.ResourceNotFoundException;
import com.bittecsoluciones.restaurantepos.Repository.AreaAttentionRepository;
import com.bittecsoluciones.restaurantepos.Service.AreaAttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaAttentionServiceImpl implements AreaAttentionService {

    @Autowired
    private AreaAttentionRepository areaAttentionRepository;
    @Override
    public List<AreaAttention> getAllAreaAttentions() {
        return areaAttentionRepository.findAll();
    }

    @Override
    public Optional<AreaAttention> getAreaAttentionById(Long id) {
        return areaAttentionRepository.findById(id);
    }

    @Override
    public AreaAttention createAreaAttention(AreaAttention areaAttention) {
        return areaAttentionRepository.save(areaAttention);
    }

    @Override
    public AreaAttention updateAreaAttention(Long id, AreaAttention areaAttention) {
        // Verificar que exista
        AreaAttention existing = areaAttentionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Area de atencion con id " + id + " no encontrada"));

        // Actualizar los campos necesarios
        existing.setName(areaAttention.getName());
        return areaAttentionRepository.save(existing);
    }

    @Override
    public void deleteAreaAttention(Long id) {
        areaAttentionRepository.deleteById(id);
    }
}
