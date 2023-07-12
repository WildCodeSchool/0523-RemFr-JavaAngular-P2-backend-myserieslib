package com.templateproject.api.repository;


import com.templateproject.api.entity.Episode;
import com.templateproject.api.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, UUID> {
    List<Episode> findBySerie(Serie serie);
    List<Episode> findBySerieId(UUID serieId);
}
