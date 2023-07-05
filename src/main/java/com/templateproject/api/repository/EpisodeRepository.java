package com.templateproject.api.repository;


import com.templateproject.api.entity.Episode;
import com.templateproject.api.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> dev
import java.util.UUID;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, UUID> {
<<<<<<< HEAD
=======
    List<Episode> findBySerie(Serie serie);
>>>>>>> dev
}
