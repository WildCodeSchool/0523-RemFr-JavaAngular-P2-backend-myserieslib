package com.templateproject.api.controller;

import com.templateproject.api.entity.Episode;
import com.templateproject.api.entity.History;
import com.templateproject.api.entity.User;
import com.templateproject.api.repository.EpisodeRepository;
import com.templateproject.api.repository.HistoryRepository;
import com.templateproject.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final EpisodeRepository episodeRepository;

    HistoryController(
            HistoryRepository historyRepositoryInjected,
            UserRepository userRepositoryInjected,
            EpisodeRepository episodeRepositoryInjected
    ) {
        this.historyRepository = historyRepositoryInjected;
        this.userRepository = userRepositoryInjected;
        this.episodeRepository = episodeRepositoryInjected;
    }

    @GetMapping("")
    public List<History> getAll() {
        return this.historyRepository.findAll();
    }

    @GetMapping("/{userId}")
    public List<History> getByUserId(@PathVariable UUID userId) {
        return this.historyRepository.findByUserId(userId);
    }

    @GetMapping("/user")
    public List<History> getByUser(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String email = (String) jwt.getClaims().get("email");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return historyRepository.findByUserId(user.getId());
    }

    @PostMapping("/add/{userId}/{episodeId}")
    public ResponseEntity<History> addHistorySelected(
            @PathVariable UUID userId,
            @PathVariable UUID episodeId
    ) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Episode episode = episodeRepository.findById(episodeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        History history = new History();
        history.setUser(user);
        history.setEpisode(episode);
        History saved = historyRepository.save(history);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/add/{episodeId}")
    public History addHistory(
            Authentication authentication,
            @PathVariable UUID episodeId
    ) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String email = (String) jwt.getClaims().get("email");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Episode episode = episodeRepository.findById(episodeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        History history = new History(user, episode);
        return historyRepository.save(history);
    }
}
