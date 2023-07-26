package com.templateproject.api.service;

import com.templateproject.api.dto.CreateSerieDTO;
import com.templateproject.api.dto.SerieDto;
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
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DtoConversionService dtoConversionService;


    public List<Serie> findAll() {
        return serieRepository.findAll();
    }

    public Serie createSerie(CreateSerieDTO newSerie) {
        Serie serie = new Serie();
        return changeOrCreateSerie(serie, newSerie);
    }

    public List<Serie> getTrendingSeries(int limit) {
        LocalDate currentDate = LocalDate.now();
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "releaseDate"));
        return serieRepository.findByReleaseDateLessThanEqual(currentDate, pageable);
    }

    public Serie getSerieById(UUID id) {
        return serieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aucune série trouvée"));
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

    public Serie updateSerie(UUID id, CreateSerieDTO newSerie) {
        Serie serieFound = serieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aucune série trouvée"));

        return changeOrCreateSerie(serieFound, newSerie);
    }

    public Serie changeOrCreateSerie(Serie serie, CreateSerieDTO newSerie) {
        serie.setName(newSerie.getName());
        serie.setDescription(newSerie.getDescription());
        serie.setReleaseDate(newSerie.getReleaseDate());
        serie.setIsCompleted(newSerie.getIsCompleted());
        serie.setPictureUrlXL(newSerie.getPictureUrlXL());
        serie.setPictureUrlXS(newSerie.getPictureUrlXS());
        serie.setProducer(newSerie.getProducer());
        serie.setTrailerURL(newSerie.getTrailerURL());

        serie.getActors().clear();
        serie.getCategories().clear();

        for (int i = 0; i<newSerie.getActors().size(); i++) {
            serie.getActors().add(newSerie.getActors().get(i));
        }
        for (int i = 0; i<newSerie.getCategory().size(); i++) {
            serie.getCategories().add(newSerie.getCategory().get(i));
        }
        return serieRepository.save(serie);
    }

    public List<SerieDto> getSeriesByCategory(UUID categoryId, int limit) {
        LocalDate currentDate = LocalDate.now();
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "releaseDate"));
        List<Serie> series = serieRepository.findByCategories_IdAndReleaseDateLessThanEqual(categoryId, currentDate, pageable);

        return series.stream()
                .map(dtoConversionService::convertToSerieDto)
                .collect(Collectors.toList());
    }


    public void delete(UUID id) {
        Serie serieToDelete = this.serieRepository.findById(id).orElseThrow();
        serieToDelete.getActors().clear();
        serieToDelete.getCategories().clear();
        this.serieRepository.delete(serieToDelete);
    }

}
