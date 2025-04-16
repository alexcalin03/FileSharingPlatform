package com.example.authservice.controller;

import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.service.UserService;

import com.example.authservice.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.example.authservice.dto.LoginRequest;
import com.example.authservice.dto.JwtResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    // Endpoint for user registration
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    // Endpoint for user login
     @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            System.out.println("Raw password: " + request.getPassword());
            System.out.println("Stored hash: " + (user != null ? user.getPassword() : "null"));
            System.out.println("Match result: " + (user != null ? passwordEncoder.matches(request.getPassword(), user.getPassword()) : "N/A"));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));

        
    }
}
