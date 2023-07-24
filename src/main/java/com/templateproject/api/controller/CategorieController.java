package com.templateproject.api.controller;

import com.github.javafaker.Cat;
import com.templateproject.api.entity.Category;
import com.templateproject.api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins="http://localhost:4200")
public class CategorieController {

    private final CategoryRepository categoryRepository;

    public CategorieController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("")
    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }

    @PostMapping("")
    public Category createCategory(@RequestParam String categoryName) {
        Category existingCategory = categoryRepository.findByName(categoryName);
        if (existingCategory != null) {
            return existingCategory;
        }
        Category newCategory = new Category(categoryName);
        return categoryRepository.save(newCategory);
    }
}
