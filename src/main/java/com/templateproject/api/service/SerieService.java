package com.templateproject.api.service;

import com.templateproject.api.entity.Actor;
import com.templateproject.api.entity.Category;
import com.templateproject.api.entity.Serie;
import com.templateproject.api.repository.ActorRepository;
import com.templateproject.api.repository.CategoryRepository;
import com.templateproject.api.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    public List<Serie> findAll() {
        return serieRepository.findAll();
    }

    public Serie createSerie(Serie serie) {
        return serieRepository.save(serie);
    }

    public List<Serie> getTrendingSeries(int limit) {
        LocalDate currentDate = LocalDate.now();
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "releaseDate"));
        return serieRepository.findByReleaseDateLessThanEqual(currentDate, pageable);
    }

    public Serie getSerieById(UUID id) {
        return serieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Serie not found"));
    }

    public List<Serie> filterSerie(String title, String filterType, UUID category) {
        String filterActor = "Actor";
        String filterProducer = "Producer";
        if (title == null || title.equals("")) {
            title = "%";
        }
        if (filterType != null && filterType.equals(filterActor)) {
            return this.serieRepository.findSeriesFromActor(title, category);
        }
        if (filterType != null && filterType.equals(filterProducer)) {
            return this.serieRepository.findSeriesFromProducer(title, category);
        }
        return this.serieRepository.findSeriesFromTitle(title, category);
    }

    public Serie updateSerie(UUID id, Serie serie, UUID actorId, UUID categoryId) {
        Serie serieFound = serieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Serie not found"));

        serieFound.setName(serie.getName());
        serieFound.setProducer(serie.getProducer());
        serieFound.setPictureUrlXL(serie.getPictureUrlXL());
        serieFound.setPictureUrlXS(serie.getPictureUrlXS());
        serieFound.setTrailerURL(serie.getTrailerURL());
        serieFound.setReleaseDate(serie.getReleaseDate());
        serieFound.setDescription(serie.getDescription());
        serieFound.setIsCompleted(serie.getIsCompleted());

        if (actorId != null) {
            Actor actorToUpdate = actorRepository.findById(actorId)
                    .orElseThrow(() -> new IllegalArgumentException("Actor not found"));
            serieFound.getActors().add(actorToUpdate);
        }

        if (categoryId != null) {
            Category categoryToUpdate = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            serieFound.getCategories().add(categoryToUpdate);
        }

        return serieRepository.save(serie);
    }

}
