package com.templateproject.api.controller;

import com.templateproject.api.dto.PasswordDTO;
import com.templateproject.api.dto.UpdateUserDTO;
import com.templateproject.api.entity.User;
import com.templateproject.api.entity.UserMail;
import com.templateproject.api.repository.UserRepository;
import com.templateproject.api.service.MailService;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserRepository userRepository;
    private final MailService mailService;

    UserController(UserRepository userRepositoryInjected, MailService mailService) {
        this.userRepository = userRepositoryInjected;
        this.mailService = mailService;
    }

    @GetMapping("")
    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    @PutMapping("")
    public User updateUser(Authentication authentication, @RequestBody UpdateUserDTO user) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String email = (String) jwt.getClaims().get("sub");
        User userInDatabase = this.userRepository.findByEmail(email).orElseThrow();
        userInDatabase.setPictureUrl(user.getPictureUrl());
        userInDatabase.setEmail(user.getEmail());
        userInDatabase.setNickname(user.getNickname());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            PasswordEncoder password = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            userInDatabase.setPassword(password.encode(user.getPassword()));
        }
        return this.userRepository.save(userInDatabase);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/active")
    public List<User> getActiveUsers() {
        return this.userRepository.findAllByDeletedFalse();
    }
    @PostMapping("")
    public User create(@RequestBody User newUser) {
        return this.userRepository.save(newUser);
    }

    @DeleteMapping("/{id}")
    public void softDelete(@PathVariable UUID id) {
        User user = this.userRepository.findById(id).orElseThrow();
        user.setDeleted(true);
        this.userRepository.save(user);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public String userAccess() {
        return "User access";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public String adminAccess() {
        return "Admin access";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN','SCOPE_ROLE_USER')")
    public String allAccess() {
        return "All access";
    }

    @GetMapping("/retrievePassword/{mail}")
    public String sendMail(@PathVariable String mail) {
        UserMail userMail = new UserMail(mail);
        User user = this.userRepository.findByEmail(userMail.getEmailAddress()).orElseThrow();
        if(user != null) {
        try {
            mailService.SendNotification(userMail);
            return "Mail sent";
        } catch (MailException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }} else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/change-password")
    public User changePassword(Authentication authentication, @RequestBody PasswordDTO newPassword) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String email = (String) jwt.getClaims().get("sub");
        User user = this.userRepository.findByEmail(email).orElseThrow();
        PasswordEncoder password = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(password.encode(newPassword.getPassword()));
        return this.userRepository.save(user);
    }

    //Find user by name
    @GetMapping("/find/{nickname}")
    public User findUserBySimilarNickname(@PathVariable String nickname) {
        List<User> allUsers = this.userRepository.findAll();
        int similarityThreshold = 2;

        Optional<User> closestMatch = allUsers.stream()
                .filter(user -> LevenshteinDistance.getDefaultInstance().apply(nickname, user.getNickname()) <= similarityThreshold)
                .min(Comparator.comparingInt(user -> LevenshteinDistance.getDefaultInstance().apply(nickname, user.getNickname())));

        return closestMatch.orElseThrow(() -> new NoSuchElementException("No user found with a similar nickname."));
    }
}
