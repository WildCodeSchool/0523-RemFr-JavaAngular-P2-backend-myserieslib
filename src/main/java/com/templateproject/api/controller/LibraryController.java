package com.templateproject.api.controller;

import com.templateproject.api.entity.Library;
import com.templateproject.api.entity.LibraryStatus;
import com.templateproject.api.entity.User;
import com.templateproject.api.repository.LibraryRepository;
import com.templateproject.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/libraries")
public class LibraryController {
    private final LibraryRepository libraryRepository;
    private final UserRepository userRepository;

    public LibraryController(LibraryRepository libraryRepositoryInjected, UserRepository userRepositoryInjected) {
        this.libraryRepository = libraryRepositoryInjected;
        this.userRepository = userRepositoryInjected;
    }

    @GetMapping("/in_progress")
    public List<Library> getInProgress() {
        User user = null;
        // récupérer l'utilisateur connecté
        return libraryRepository.findByUserStatus(
                user,LibraryStatus.IN_PROGRESS
        );
    }

    @GetMapping("/{id}/libraries")
    public List<Library> getLibrariesByUserId(@PathVariable UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (user != null) {
            return libraryRepository.findByUserId(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/series/{id}")
    public void deleteSeries(@PathVariable UUID id) {
        this.libraryRepository.deleteById(id);
    }
}
