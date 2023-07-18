package com.templateproject.api.controller;

import com.templateproject.api.dto.LoginResponse;
import com.templateproject.api.entity.Role;
import com.templateproject.api.entity.User;
import com.templateproject.api.repository.RoleRepository;
import com.templateproject.api.repository.UserRepository;
import com.templateproject.api.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    public AuthController(
            UserRepository userRepositoryInjected,
            RoleRepository roleRepositoryInjected,
            AuthenticationManager authManagerInjected,

            TokenService tokenService) {
        this.userRepository = userRepositoryInjected;
        this.roleRepository = roleRepositoryInjected;
        this.authManager = authManagerInjected;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User newUser) {
        if (this.userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User already exists with email " + newUser.getEmail());
        }
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        Role userRole = this.roleRepository.findByName("ROLE_USER")
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "No ROLE_USER found"));
        newUser.setRole((Role) Set.of(userRole));
        return this.userRepository.save(newUser);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody User user) {

        Authentication auth = this.authManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword()
        ));
        String token = tokenService.generateToken(auth);
        User userConnected = (User) auth.getPrincipal();

        return new LoginResponse(token, userConnected);
    }
}
