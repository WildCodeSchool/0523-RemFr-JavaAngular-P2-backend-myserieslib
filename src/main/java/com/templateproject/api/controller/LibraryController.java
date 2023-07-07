package com.templateproject.api.controller;

import com.templateproject.api.entity.Library;
import com.templateproject.api.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/libraries")
@CrossOrigin(origins="http://localhost:4200")
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;

    }

    @GetMapping("")
    public List<Library> findAll() {
        List<Library> libraries = libraryService.findAll();
        return libraries;
    }

    @GetMapping("/{serieId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable UUID serieId) {
        Double averageRating = libraryService.getAverageRatings(serieId);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }
}
