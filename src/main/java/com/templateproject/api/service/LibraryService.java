package com.templateproject.api.service;

import com.templateproject.api.entity.*;
import com.templateproject.api.repository.EpisodeRepository;
import com.templateproject.api.repository.LibraryRepository;
import com.templateproject.api.repository.SerieRepository;

import com.templateproject.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


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
}
