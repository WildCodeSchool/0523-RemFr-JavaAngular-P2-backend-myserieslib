package com.templateproject.api.controller;

import com.templateproject.api.entity.User;
import com.templateproject.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserRepository userRepository;

    UserController(UserRepository userRepositoryInjected) {
        this.userRepository = userRepositoryInjected;
    }

    @GetMapping("")
    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("")
    public User create(@RequestBody User newUser) {
        return this.userRepository.save(newUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        this.userRepository.deleteById(id);
    }
}
