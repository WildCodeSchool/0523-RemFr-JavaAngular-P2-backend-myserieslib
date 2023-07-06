package com.templateproject.api.repository;


import com.templateproject.api.entity.Serie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface SerieRepository extends JpaRepository<Serie, UUID> {
     List<Serie> findByReleaseDateLessThanEqual(LocalDate date, Pageable pageable);

    @Query("SELECT s FROM Serie s JOIN s.categories c WHERE s.name LIKE CONCAT('%', :title, '%')  AND (c.name = :category OR :category is null)")
    List<Serie> findSeriesFromTitle(@Param("title") String title, @Param("category") String category);

    @Query("SELECT s FROM Serie s JOIN s.categories c JOIN s.actors a WHERE CONCAT(a.firstName ,' ', a.lastName) LIKE CONCAT('%', :title, '%')  AND (c.name = :category OR :category is null)")
    List<Serie> findSeriesFromActor(@Param("title") String title, @Param("category") String category);

    @Query("SELECT s FROM Serie s JOIN s.categories c  WHERE s.producer LIKE CONCAT('%', :title, '%')  AND (c.name = :category OR :category is null)")
    List<Serie> findSeriesFromProducer(@Param("title") String title, @Param("category") String category);

}
