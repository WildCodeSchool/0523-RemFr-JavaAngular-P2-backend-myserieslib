package com.templateproject.api.controller;

import com.templateproject.api.entity.Library;
import com.templateproject.api.entity.LibraryStatus;
import com.templateproject.api.entity.User;
import com.templateproject.api.repository.LibraryRepository;
import com.templateproject.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.templateproject.api.service.LibraryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/librairies")
public class LibraryController {
    private final LibraryRepository libraryRepository;
    private final UserRepository userRepository;
    private final LibraryService libraryService;

    public LibraryController(LibraryRepository libraryRepositoryInjected, UserRepository userRepositoryInjected, LibraryService libraryService) {
        this.libraryRepository = libraryRepositoryInjected;
        this.userRepository = userRepositoryInjected;
        this.libraryService = libraryService;
    }

    @GetMapping("")
    public List<Library> getAll() {
        return this.libraryRepository.findAll();
    }

    @GetMapping("/{userId}")
    public List<Library> getAllUserSeries(@PathVariable UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (user != null) {
            return libraryRepository.findByUserId(userId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}/in_progress")
    public List<Library> getInProgress(@PathVariable UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return libraryRepository.findByUserAndStatus(user, LibraryStatus.IN_PROGRESS);
    }

    @DeleteMapping("/{id}")
    public void deleteSeries(@PathVariable UUID id) {
        this.libraryRepository.deleteById(id);
    }
}