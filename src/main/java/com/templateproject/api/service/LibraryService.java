package com.templateproject.api.service;

import com.templateproject.api.dto.CategoryDto;
import com.templateproject.api.entity.*;
import com.templateproject.api.repository.EpisodeRepository;
import com.templateproject.api.repository.LibraryRepository;
import com.templateproject.api.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import com.templateproject.api.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DtoConversionService dtoConversionService;

    public List<Library> findAll() {
        return libraryRepository.findAll();
    }

    public LibraryStatus getProgressInSerie(UUID serieId, UUID userId) {
        Serie serie = serieRepository.findById(serieId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        int nbTotalOfEpisodes = episodeRepository.findBySerie(serie).size();
        int nbTotalOfEpisodesSeenByUser = user.getEpisodes().size();
        if (nbTotalOfEpisodes == nbTotalOfEpisodesSeenByUser && serie.getIsCompleted()) {
            return LibraryStatus.FINISHED;
        } else if (nbTotalOfEpisodesSeenByUser == 0) {
            return LibraryStatus.NOT_STARTED;
        }
        return LibraryStatus.IN_PROGRESS;
    };

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

    public List<CategoryDto> getMostFrequentCategories(UUID userId, int limit) {
        List<Library> libraries = libraryRepository.findByUserId(userId);
        Map<Category, Integer> categoryCount = new HashMap<>();

        for (Library library : libraries) {
            if (library.getStatus() == LibraryStatus.NOT_STARTED) {
                continue;
            }
            List<Category> categories = library.getSerie().getCategories();
            for (Category category : categories) {
                categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
            }
        }

        List<Category> mostFrequentCategories = categoryCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return mostFrequentCategories.stream()
                .map(dtoConversionService::convertToCategoryDto)
                .collect(Collectors.toList());
    }


}
