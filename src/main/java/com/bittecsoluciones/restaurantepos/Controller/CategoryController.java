package com.bittecsoluciones.restaurantepos.Controller;

import com.bittecsoluciones.restaurantepos.Entity.Category;
import com.bittecsoluciones.restaurantepos.Exceptions.ResourceNotFoundException;
import com.bittecsoluciones.restaurantepos.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Crear nueva categoria
    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        Category created = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Obtener todas las categorias
    @GetMapping("/list")
    public ResponseEntity<List<Category>> getAllCategorys() {
        return ResponseEntity.ok(categoryService.getAllCategorys());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor con id " + id + " no encontrada"));
        return ResponseEntity.ok(category);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody Category category) {

        Category updated = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(updated);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
