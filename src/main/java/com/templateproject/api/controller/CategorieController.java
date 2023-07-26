package com.templateproject.api.controller;

import com.github.javafaker.Cat;
import com.templateproject.api.dto.CategoryDto;
import com.templateproject.api.entity.Category;
import com.templateproject.api.repository.CategoryRepository;
import com.templateproject.api.service.DtoConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins="http://localhost:4200")
public class CategorieController {

    private final CategoryRepository categoryRepository;
    private final DtoConversionService dtoConversionService;

    public CategorieController(CategoryRepository categoryRepository, DtoConversionService dtoConversionService) {
        this.categoryRepository = categoryRepository;
        this.dtoConversionService = dtoConversionService;
    }

    @GetMapping("")
    public List<Category> getCategories() {
        return this.categoryRepository.findAllWithSeries();
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

    @GetMapping("/categoriesWithSeries")
    public List<CategoryDto> getAllCategoriesWithSeries() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(dtoConversionService::convertToCategoryDto).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryRepository.deleteById(id);
    }
}
