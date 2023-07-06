package com.templateproject.api.service;

import com.templateproject.api.entity.Actor;
import com.templateproject.api.entity.Category;
import com.templateproject.api.entity.Serie;
import com.templateproject.api.repository.ActorRepository;
import com.templateproject.api.repository.CategoryRepository;
import com.templateproject.api.repository.SerieRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class SerieService {

    private final SerieRepository serieRepository;
    private final ActorRepository actorRepository;
    private final CategoryRepository categoryRepository;
    private EntityManager entityManager;


    public SerieService(SerieRepository serieRepository, ActorRepository actorRepository, CategoryRepository categoryRepository) {
        this.serieRepository = serieRepository;
        this.actorRepository = actorRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Serie> findAll() {
        return serieRepository.findAll();
    }
    public Serie createSerie(Serie serie) {
        return serieRepository.save(serie);
    }

    public List<Serie> getTrendingSeries() {
        LocalDate currentDate = LocalDate.now();
        return serieRepository.findByReleaseDateLessThanEqualOrderByReleaseDateDesc(currentDate, PageRequest.of(0, 15));
    }

    public Serie getSerieById(UUID id) {
        return serieRepository.findById(id)
              .orElseThrow(() -> new IllegalArgumentException("Serie not found"));
    }

    public Serie updateSerie( UUID id, Serie serie, UUID actorId, UUID categoryId) {
        Serie serieFound = serieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Serie not found"));
        System.out.println(serieFound.getName());

        serieFound.setName(serie.getName());
        serieFound.setProducer(serie.getProducer());
        serieFound.setPictureUrl(serie.getPictureUrl());
        serieFound.setTrailerURL(serie.getTrailerURL());
        serieFound.setReleaseDate(serie.getReleaseDate());
        serieFound.setDescription(serie.getDescription());
        serieFound.setIsCompleted(serie.getIsCompleted());

        if(actorId!= null) {
            Actor actorToUpdate = actorRepository.findById(actorId)
                    .orElseThrow(() -> new IllegalArgumentException("Actor not found"));
            serieFound.getActors().add(actorToUpdate);
        }

        if(categoryId!= null) {
            Category categoryToUpdate = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            serieFound.getCategories().add(categoryToUpdate);
        }

        return serieRepository.save(serieFound);
    }

}
