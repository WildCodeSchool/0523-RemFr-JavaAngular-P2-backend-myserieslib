package com.templateproject.api.repository;

import com.templateproject.api.entity.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Category findByName(String categoryName);

    @Query("SELECT c FROM Category c JOIN FETCH c.series")
    List<Category> findAllWithSeries();
}
