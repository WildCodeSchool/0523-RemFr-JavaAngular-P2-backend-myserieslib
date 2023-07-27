package com.templateproject.api.controller;

import com.templateproject.api.dto.CategoryDto;
import com.templateproject.api.entity.*;
import com.templateproject.api.repository.LibraryProjection;
import com.templateproject.api.repository.LibraryRepository;
import com.templateproject.api.repository.SerieRepository;
import com.templateproject.api.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.templateproject.api.service.LibraryService;

import java.util.*;

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

    @GetMapping("/filtered")
    public List<LibraryProjection> GetAllWithOffset(Pageable pageable) {
        return this.libraryRepository.findWithComments(pageable).getContent();
    }

    @GetMapping("/{serieId}/ratings")
    public ResponseEntity<Double> getAverageRating(@PathVariable UUID serieId) {
        Double averageRating = libraryService.getAverageRatings(serieId);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }

    @GetMapping("/series/ratings")
    public List<Map<String, Object>> getAllAverageRatings() {
        return this.libraryService.getAllAverageRatings();
    }

    @GetMapping("/{serieId}/comments")
    public List<LibraryProjection> getAllComments(@PathVariable UUID serieId) {
        Serie serie = this.serieRepository.findById(serieId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return libraryRepository.findBySerieId(serie.getId());
    }

    @GetMapping("/{userId}")
    public List<Library> getAllUserSeriesSelected(@PathVariable UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return libraryRepository.findByUserId(userId);
    }

    @GetMapping("/user")
    public List<Library> getAllUserSeries(Authentication authentication) {
        if (authentication!=null) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            return libraryRepository.findByUserId(user.getId());
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vous devez vous identifier");
    }

    @GetMapping("/in_progress")
    public List<Library> getInProgress(Authentication authentication) {
        if (authentication!=null) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            return libraryRepository.findByUserAndStatus(user, LibraryStatus.IN_PROGRESS);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vous devez vous identifier");
    }

    @GetMapping("/not_started")
    public List<Library> getNotStarted(Authentication authentication) {
        if (authentication !=null) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            return libraryRepository.findByUserAndStatus(user, LibraryStatus.NOT_STARTED);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vous devez vous identifier");
    }

    @GetMapping("/finished")
    public List<Library> getFinished(Authentication authentication) {
        if (authentication!=null) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            return libraryRepository.findByUserAndStatus(user, LibraryStatus.FINISHED);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vous devez vous identifier");
    }

    @GetMapping("/recently_seen")
    public List<Library> getRecentlySeen(Authentication authentication) {
        if (authentication!=null) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            return libraryRepository.findByUserAndStatus(user, LibraryStatus.RECENTLY_SEEN);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vous devez vous identifier");
    }

    @GetMapping("/series/{serieId}")
    public ResponseEntity<Library> getUserSerieDetails(
            @PathVariable UUID serieId,
            Authentication authentication
    ) {
        Serie serie = this.serieRepository.findById(serieId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(authentication != null) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            Library library = libraryRepository.findByUserAndSerie(user, serie);
            return ResponseEntity.ok(library);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vous devez vous identifier");
    }

    @PostMapping("/add/{serieId}")
    public Library postLibrary(Authentication authentication, @PathVariable UUID serieId) {
        if (authentication!=null) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            Serie serie = this.serieRepository.findById(serieId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            Library library = new Library(user, serie);
            return libraryRepository.save(library);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vous devez vous identifier");
    }

    @PostMapping("/{serieId}/comments/{userId}")
    public Library postComment(@PathVariable UUID serieId, @PathVariable UUID userId, @RequestParam String score, @RequestParam String comment) {
        Library library = libraryRepository.findBySerieIdAndUserId(serieId, userId);
        LibraryStatus status = libraryService.getProgressInSerie(serieId, userId);
        if(status.toString().equals("NOT_STARTED")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vous devez avoir vu au moins 1 épisode pour commenter la série");
        }
        library.setComment(comment);
        library.setScore(Integer.valueOf(score));
        return libraryRepository.save(library);
    }


    @PutMapping("/series/{serieId}/score")
    public ResponseEntity<Library> updateScore(
            Authentication authentication,
            @PathVariable UUID serieId,
            @RequestBody Map<String, Integer> scoreMap
            ) {
        if (authentication!=null) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            Optional<User> optionalUser = userRepository.findByEmail(email);
            Optional<Serie> optionalSerie = serieRepository.findById(serieId);

        if (optionalUser.isPresent() && optionalSerie.isPresent()) {
            User user = optionalUser.get();
            Serie serie = optionalSerie.get();
            Library library = libraryRepository.findByUserAndSerie(user, serie);

            if (library != null) {
                Integer score = scoreMap.get("score");
                library.setScore(score);
                Library updated = libraryRepository.save(library);
                return ResponseEntity.ok(updated);
            }
        }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/series/{seriedId}/comment")
    public ResponseEntity<Library> updateComment(
            Authentication authentication,
            @PathVariable UUID seriedId,
            @RequestBody Map<String, String> commentMap
    ) {
        if (authentication != null) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            Optional<User> optionalUser = userRepository.findByEmail(email);
            Optional<Serie> optionalSerie = serieRepository.findById(seriedId);

            if (optionalUser.isPresent() && optionalSerie.isPresent()) {
                User user = optionalUser.get();
                Serie serie = optionalSerie.get();
                Library library = libraryRepository.findByUserAndSerie(user, serie);

                if (library != null) {
                    String comment = commentMap.get("comment");
                    library.setComment(comment);
                    Library updated = libraryRepository.save(library);
                    return ResponseEntity.ok(updated);
                }
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/series/{serieId}/checkboxe-episode")
    public ResponseEntity<Library> updateCheckEpisode(
            Authentication authentication,
            @PathVariable UUID serieId,
            @RequestBody Map<String, List<Integer>> checkboxeMap
    ) {
        if (authentication != null) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            Optional<User> optionalUser = userRepository.findByEmail(email);
            Optional<Serie> optionalSerie = serieRepository.findById(serieId);

            if (optionalUser.isPresent() && optionalSerie.isPresent()) {
                User user = optionalUser.get();
                Serie serie = optionalSerie.get();
                Library library = libraryRepository.findByUserAndSerie(user, serie);

                if (library != null) {
                    List<Integer> checkboxes = checkboxeMap.get("checkboxes");
                    library.setCheckedEpisodes(checkboxes);
                    Library updated = libraryRepository.save(library);
                    return ResponseEntity.ok(updated);
                }
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/series/{serieId}/status")
    public ResponseEntity<Library> updateStatus(
            Authentication authentication,
            @PathVariable UUID serieId,
            @RequestBody Map<String, String> statusMap
    ) {
        if (authentication!= null) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            Optional<User> optionalUser = userRepository.findByEmail(email);
            Optional<Serie> optionalSerie = serieRepository.findById(serieId);

            if (optionalUser.isPresent() && optionalSerie.isPresent()) {
                User user = optionalUser.get();
                Serie serie = optionalSerie.get();
                Library library = libraryRepository.findByUserAndSerie(user, serie);

                if (library != null) {
                    String status = statusMap.get("status");
                    LibraryStatus libraryStatus;
                    libraryStatus = LibraryStatus.valueOf(status.toUpperCase());
                    library.setStatus(libraryStatus);
                    Library updateLibrary = libraryRepository.save(library);
                    return ResponseEntity.ok(updateLibrary);
                }
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public void deleteSeries(@PathVariable UUID id) {
        this.libraryRepository.deleteById(id);
    }

    @GetMapping("/users/{userId}/frequent-categories")
    public List<CategoryDto> getMostFrequentCategories(@PathVariable UUID userId, @RequestParam(defaultValue = "5") int limit) {
        return libraryService.getMostFrequentCategories(userId, limit);
    }

    @PutMapping("/{id}")
    public void deleteComment(@PathVariable UUID id) {
        Library library = this.libraryRepository.findById(id).orElseThrow();
        library.setComment(null);
        libraryRepository.save(library);
    }
}