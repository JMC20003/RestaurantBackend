package com.bittecsoluciones.restaurantepos.ServiceImpl;

import com.bittecsoluciones.restaurantepos.DTOs.IngredientRequestDTO;
import com.bittecsoluciones.restaurantepos.DTOs.IngredientResponseDTO;
import com.bittecsoluciones.restaurantepos.Entity.Ingredient;
import com.bittecsoluciones.restaurantepos.Entity.Supplier;
import com.bittecsoluciones.restaurantepos.Entity.UnitMeasure;
import com.bittecsoluciones.restaurantepos.Repository.IngredientRepository;
import com.bittecsoluciones.restaurantepos.Repository.SupplierRepository;
import com.bittecsoluciones.restaurantepos.Repository.UnitMeasureRepository;
import com.bittecsoluciones.restaurantepos.Service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private UnitMeasureRepository unitMeasureRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    private IngredientResponseDTO mapToDTO(Ingredient ingredient) {
        return new IngredientResponseDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getDescription(),
                ingredient.getCostPerUnit(),
                ingredient.getMinimumStock(),
                ingredient.getCurrentStock(),
                ingredient.getUnitMeasure() != null ? ingredient.getUnitMeasure().getName() : null,
                ingredient.getSupplier() != null ? ingredient.getSupplier().getName() : null,
                ingredient.getActive(),
                ingredient.getCreatedAt(),
                ingredient.getUpdatedAt()
        );
    }

    @Override
    public List<IngredientResponseDTO> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public IngredientResponseDTO getIngredientById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));
        return mapToDTO(ingredient);
    }

    @Override
    public IngredientResponseDTO createIngredient(IngredientRequestDTO ingredientRequest) {
        UnitMeasure unitMeasure = unitMeasureRepository.findById(ingredientRequest.unitMeasureId())
                .orElseThrow(() -> new RuntimeException("Unidad de medida no encontrada"));

        Supplier supplier = null;
        if (ingredientRequest.supplierId() != null) {
            supplier = supplierRepository.findById(ingredientRequest.supplierId())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        }

        Ingredient ingredient = Ingredient.builder()
                .name(ingredientRequest.name())
                .description(ingredientRequest.description())
                .costPerUnit(ingredientRequest.costPerUnit())
                .minimumStock(ingredientRequest.minimumStock())
                .currentStock(ingredientRequest.currentStock())
                .unitMeasure(unitMeasure)
                .supplier(supplier)
                .active(ingredientRequest.active() != null ? ingredientRequest.active() : true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        ingredientRepository.save(ingredient);

        return mapToDTO(ingredient);
    }

    @Override
    public IngredientResponseDTO updateIngredient(Long id, IngredientRequestDTO ingredientRequest) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

        UnitMeasure unitMeasure = unitMeasureRepository.findById(ingredientRequest.unitMeasureId())
                .orElseThrow(() -> new RuntimeException("Unidad de medida no encontrada"));

        Supplier supplier = null;
        if (ingredientRequest.supplierId() != null) {
            supplier = supplierRepository.findById(ingredientRequest.supplierId())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        }

        ingredient.setName(ingredientRequest.name());
        ingredient.setDescription(ingredientRequest.description());
        ingredient.setCostPerUnit(ingredientRequest.costPerUnit());
        ingredient.setMinimumStock(ingredientRequest.minimumStock());
        ingredient.setCurrentStock(ingredientRequest.currentStock());
        ingredient.setUnitMeasure(unitMeasure);
        ingredient.setSupplier(supplier);
        ingredient.setActive(ingredientRequest.active());
        ingredient.setUpdatedAt(LocalDateTime.now());

        ingredientRepository.save(ingredient);

        return mapToDTO(ingredient);
    }

    @Override
    public void deleteIngredient(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));
        ingredientRepository.delete(ingredient);
    }

    @Override
    public Optional<IngredientResponseDTO> getIngredientByName(String name) {
        return Optional.empty();
    }
}
