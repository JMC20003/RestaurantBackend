package com.bittecsoluciones.restaurantepos.ServiceImpl;

import com.bittecsoluciones.restaurantepos.Entity.Category;
import com.bittecsoluciones.restaurantepos.Entity.Supplier;
import com.bittecsoluciones.restaurantepos.Exceptions.ResourceNotFoundException;
import com.bittecsoluciones.restaurantepos.Repository.CategoryRepository;
import com.bittecsoluciones.restaurantepos.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategorys() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id)  ;
    }

    @Override
    public Category createCategory(Category category) {
        category.setCreatedAt(LocalDateTime.now());
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        // Verificar que exista
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ctegoria con id " + id + " no encontrada"));

        // Actualizar los campos necesarios
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        existing.setImage(category.getImage());
        existing.setActive(category.getActive());
        existing.setUpdatedAt(LocalDateTime.now());
        return categoryRepository.save(existing);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
