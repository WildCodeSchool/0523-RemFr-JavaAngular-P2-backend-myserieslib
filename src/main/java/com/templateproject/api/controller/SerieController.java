package com.templateproject.api.controller;

import com.templateproject.api.entity.Serie;
import com.templateproject.api.repository.SerieRepository;
import com.templateproject.api.service.SerieService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/serie")
public class SerieController {

    private final SerieService serieService;
    private final SerieRepository serieRepository;

    public SerieController(SerieService serieService, SerieRepository serieRepository) {
        this.serieService = serieService;
        this.serieRepository = serieRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Serie>> findALl() {
        List<Serie> serieList = serieService.findAll();
        return new ResponseEntity<>(serieList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Serie> create(@RequestBody Serie serie) {
        Serie createdSerie = serieService.createSerie(serie);
        return new ResponseEntity<>(createdSerie, HttpStatus.CREATED);
    }

    @GetMapping("/trending")
    public ResponseEntity<List<Serie>> getTrendingSeries() {
        List<Serie> trendingSeries = serieService.getTrendingSeries();
        return ResponseEntity.ok(trendingSeries);
    }

    @PutMapping("/update/{id}")
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
}
