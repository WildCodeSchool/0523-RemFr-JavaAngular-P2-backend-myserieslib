package com.templateproject.api.controller;

import com.templateproject.api.dto.CreateSerieDTO;
import com.templateproject.api.entity.Serie;
import com.templateproject.api.service.SerieService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/series")
@CrossOrigin(origins="http://localhost:4200")
public class SerieController {

    private final SerieService serieService;

    public SerieController(SerieService serieService) {
        this.serieService = serieService;
    }

    @PostMapping("")
    public ResponseEntity<Serie> create(@RequestBody CreateSerieDTO newSerie) {
        Serie createdSerie = serieService.createSerie(newSerie);
        return new ResponseEntity<>(createdSerie, HttpStatus.CREATED);
    }

    @GetMapping("/trending")
    public ResponseEntity<List<Serie>> getTrendingSeries() {
        List<Serie> trendingSeries = serieService.getTrendingSeries(5);
        return new ResponseEntity<>(trendingSeries, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Serie> update(@PathVariable("id") UUID id,
                                        @RequestBody Serie serie,
                                        @RequestParam(required = false) UUID actorId,
                                        @RequestParam(required = false) UUID categoryId) {
        Serie updatedSerie = serieService.updateSerie(id, serie, actorId, categoryId);
        return new ResponseEntity<>(updatedSerie, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serie> findById(@PathVariable("id") UUID id) {
        Serie serie = serieService.getSerieById(id);
        return new ResponseEntity<>(serie, HttpStatus.OK);
    }

    @GetMapping("")
    public List<Serie> getSeries(@RequestParam(name="title", required = false) String title, @RequestParam(name="filter", required=false) String filterType, @RequestParam(name="category", required = false) UUID category) {
        return this.serieService.filterSerie(title, filterType, category);
    }

    @GetMapping("/categories/{categoryId}/series")
    public ResponseEntity<List<Serie>> getTopSeriesByCategory(@PathVariable UUID categoryId, @RequestParam int limit) {
        List<Serie> series = serieService.getSeriesByCategory(categoryId, limit);
        return new ResponseEntity<>(series, HttpStatus.OK);
    }

}
