package com.templateproject.api.controller;

import com.templateproject.api.entity.Library;
import com.templateproject.api.entity.LibraryStatus;
import com.templateproject.api.entity.Serie;
import com.templateproject.api.entity.User;
import com.templateproject.api.repository.LibraryProjection;
import com.templateproject.api.repository.LibraryRepository;
import com.templateproject.api.repository.SerieRepository;
import com.templateproject.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.templateproject.api.service.LibraryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/libraries")
@CrossOrigin(origins="http://localhost:4200")
public class LibraryController {
    private final LibraryRepository libraryRepository;
    private final UserRepository userRepository;
    private final SerieRepository serieRepository;
    private final LibraryService libraryService; 

    public LibraryController(LibraryRepository libraryRepositoryInjected, UserRepository userRepositoryInjected, SerieRepository serieRepositoryInjected, LibraryService libraryService) {
        this.libraryRepository = libraryRepositoryInjected;
        this.userRepository = userRepositoryInjected;
        this.serieRepository = serieRepositoryInjected;
        this.libraryService = libraryService;
    }

    @GetMapping("")
    public List<Library> getAll() {
        return this.libraryRepository.findAll();
    }

    @GetMapping("/{serieId}/ratings")
    public ResponseEntity<Double> getAverageRating(@PathVariable UUID serieId) {
        Double averageRating = libraryService.getAverageRatings(serieId);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }

    @GetMapping("/{serieId}/comments")
    public List<LibraryProjection> getAllComments(@PathVariable UUID serieId) {
        Serie serie = this.serieRepository.findById(serieId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return libraryRepository.findBySerieId(serie.getId());
    }

    @GetMapping("/{userId}")
    public List<Library> getAllUserSeries(@PathVariable UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return libraryRepository.findByUserId(user.getId());
    }

    @GetMapping("/{userId}/in_progress")
    public List<Library> getInProgress(@PathVariable UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return libraryRepository.findByUserAndStatus(user, LibraryStatus.IN_PROGRESS);
    }

    @GetMapping("/{userId}/finished")
    public List<Library> getFinished(@PathVariable UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return libraryRepository.findByUserAndStatus(user, LibraryStatus.FINISHED);
    }

    @DeleteMapping("/{id}")
    public void deleteSeries(@PathVariable UUID id) {
        this.libraryRepository.deleteById(id);
    }
}