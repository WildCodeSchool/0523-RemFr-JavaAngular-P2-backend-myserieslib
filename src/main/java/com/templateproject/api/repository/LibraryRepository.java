package com.templateproject.api.repository;


import com.templateproject.api.entity.Library;
import com.templateproject.api.entity.LibraryStatus;
import com.templateproject.api.entity.Serie;
import com.templateproject.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LibraryRepository extends JpaRepository<Library, UUID> {
    Library findBySerieIdAndUserId(UUID serieId, UUID userId);
    List<Library> findByUserAndStatus(User user, LibraryStatus status);
    List<Library> findByUserId(UUID userId);
    List<Library> findLibrariesBySerieId(UUID serieId);
    Library findByUserAndSerie(User user, Serie serie);
    List<LibraryProjection> findBySerieId(UUID serieId);
}
