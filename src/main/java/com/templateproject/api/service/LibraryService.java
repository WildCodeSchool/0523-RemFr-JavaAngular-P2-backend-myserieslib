package com.templateproject.api.service;

import com.templateproject.api.entity.Library;
import com.templateproject.api.entity.Serie;
import com.templateproject.api.repository.LibraryRepository;
import com.templateproject.api.repository.SerieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;


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
        List<Library> libraries = libraryRepository.findSerieById(serieId);
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
}
