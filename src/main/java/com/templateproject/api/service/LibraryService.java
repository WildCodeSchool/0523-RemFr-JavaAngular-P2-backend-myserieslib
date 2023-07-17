package com.templateproject.api.service;

import com.templateproject.api.entity.Library;
import com.templateproject.api.entity.Serie;
import com.templateproject.api.repository.LibraryRepository;
import com.templateproject.api.repository.SerieRepository;

import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private SerieRepository serieRepository;


    public List<Library> findAll() {
        return libraryRepository.findAll();
    }

    public Double getAverageRatings(UUID serieId) {
        List<Library> libraries = libraryRepository.findLibrariesBySerieId(serieId);
        if (libraries.isEmpty()) {
            return 0.0;
        }
        int totalRating = 0;
        int numberOfRatings = 0;
        for (Library library : libraries) {
            totalRating += library.getScore();
            numberOfRatings++;
        }
        if (numberOfRatings == 0) {
            return 0.0;
        }
        return (double) totalRating / (double) numberOfRatings;
    }

    public List<Map<String, Object>> getAllAverageRatings() {
        List<Map<String, Object>> seriesRatings = new ArrayList<>();
        List<Serie> series = serieRepository.findAll();
        for (Serie serie : series) {
            Double rating = this.getAverageRatings(serie.getId());
            Map<String, Object> serieRating = new HashMap<>();
            serieRating.put("id", serie.getId());
            serieRating.put("score", rating);
            seriesRatings.add(serieRating);
        }

        Collections.sort(seriesRatings, (a, b) -> Double.compare((Double) b.get("score"), (Double) a.get("score")));

        return seriesRatings;
    }
}
