package com.bittecsoluciones.restaurantepos.Service;


import com.bittecsoluciones.restaurantepos.Entity.Category;
import com.bittecsoluciones.restaurantepos.Entity.Supplier;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategorys();
    Optional<Category> getCategoryById(Long id);
    Category createCategory(Category category);
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
}
