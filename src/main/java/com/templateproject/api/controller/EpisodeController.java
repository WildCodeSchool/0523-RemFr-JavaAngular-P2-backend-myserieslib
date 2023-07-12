package com.templateproject.api.controller;

import com.templateproject.api.entity.Episode;
import com.templateproject.api.repository.EpisodeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/episodes")
@CrossOrigin(origins="http://localhost:4200")
public class EpisodeController {
    private EpisodeRepository episodeRepository;

    public EpisodeController(EpisodeRepository episodeRepositoryInjected) {
        this.episodeRepository = episodeRepositoryInjected;
    }

    @GetMapping("")
    public List<Episode> getAll() {
        return this.episodeRepository.findAll();
    }

    @GetMapping("/series/{serieId}")
    public List<Episode> getAllBySerieId(@PathVariable UUID serieId) {
        return  this.episodeRepository.findBySerieId(serieId);
    }
}
