package com.templateproject.api.controller;

import com.templateproject.api.entity.Episode;
import com.templateproject.api.entity.Serie;
import com.templateproject.api.repository.EpisodeRepository;
import com.templateproject.api.repository.SerieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/episodes")
@CrossOrigin(origins="http://localhost:4200")
public class EpisodeController {
    private final EpisodeRepository episodeRepository;
    private final SerieRepository serieRepository;

    public EpisodeController(EpisodeRepository episodeRepositoryInjected, SerieRepository serieRepositoryInjected) {
        this.episodeRepository = episodeRepositoryInjected;
        this.serieRepository = serieRepositoryInjected;
    }

    @GetMapping("")
    public List<Episode> getAll() {
        return this.episodeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Episode getById(@PathVariable UUID id) {
        return this.episodeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/series/{serieId}")
    public List<Episode> getAllBySerieId(@PathVariable UUID serieId) {
        return  this.episodeRepository.findBySerieId(serieId);
    }

    @PostMapping("/series/{serieId}/add")
    public Episode createEpisode(
            @PathVariable UUID serieId,
            @RequestBody Episode episode
    ) {
        Serie serie = serieRepository.findById(serieId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        episode.setSerie(serie);
        return episodeRepository.save(episode);
    }

    @PutMapping("/{id}")
    public Episode updateEpisode(
            @PathVariable UUID id,
            @RequestBody Episode episode
    ) {
        Episode episodeExist = episodeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        episodeExist.setDescription(episode.getDescription());
        episodeExist.setEpisodeNumber(episode.getEpisodeNumber());
        episodeExist.setTitle(episode.getTitle());
        episodeExist.setSeasonNumber(episode.getSeasonNumber());
        episodeExist.setReleaseDate(episode.getReleaseDate());
        episodeExist.setThumbnail(episode.getThumbnail());

        return episodeRepository.save(episodeExist);
    }

    @DeleteMapping("/{id}")
    public void deleteEpisode(@PathVariable UUID id) { this.episodeRepository.deleteById(id); }
}
