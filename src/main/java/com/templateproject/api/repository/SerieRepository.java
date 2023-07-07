package com.templateproject.api.repository;


import com.templateproject.api.entity.Serie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Repository
public interface SerieRepository extends JpaRepository<Serie, UUID> {
    List<Serie> findSerieByIsCompleted(Boolean isCompleted);
     List<Serie> findByReleaseDateLessThanEqual(LocalDate date, Pageable pageable);

}
