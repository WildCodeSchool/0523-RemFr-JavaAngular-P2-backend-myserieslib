package com.templateproject.api.controller;

import com.templateproject.api.entity.Category;
import com.templateproject.api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
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
}
