package com.templateproject.api.repository;


import com.templateproject.api.entity.Library;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LibraryRepository extends JpaRepository<Library, UUID> {
    List<Library> findSerieById(UUID serieId);

    List<Library> findLibrariesBySerieId(UUID serieId);
}
