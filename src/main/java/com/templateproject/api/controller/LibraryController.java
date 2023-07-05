package com.templateproject.api.controller;

import com.templateproject.api.entity.Library;
import com.templateproject.api.entity.Serie;
import com.templateproject.api.repository.SerieRepository;
import com.templateproject.api.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/library")
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;

    }

    @GetMapping("/all")
    public ResponseEntity<List<Library>> findAll() {
        List<Library> libraries = libraryService.findAll();
        return new ResponseEntity<>(libraries, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Library> addSerieToLibrary(@RequestBody Library library) {
        Library newLibrary = libraryService.create(library);
        return new ResponseEntity<>(newLibrary, HttpStatus.CREATED);
    }
    @PostMapping("/add_to_library/serie")
    public ResponseEntity<Library> addSerieToLibrary(@RequestBody Library library, @RequestParam UUID serieId) {
        Library newLibrary = libraryService.addSerieToLibrary(library.getId(), serieId);
        return new ResponseEntity<>(newLibrary, HttpStatus.CREATED);
    }

    @GetMapping("/{serieId}/score")
    public ResponseEntity<Double> getAverageRating(@PathVariable UUID serieId) {
        Double averageRating = libraryService.getAverageRatings(serieId);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }
}
