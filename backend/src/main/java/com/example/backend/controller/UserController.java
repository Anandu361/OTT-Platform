package com.example.backend.controller;

import com.example.backend.models.UserModel;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.TokenGenerator;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.backend.dto.PasswordRequest;
import com.example.backend.service.CustomUserDetail;
import com.example.backend.service.CustomUserDetailsService;



@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private TokenGenerator tokenGenerator;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel requestUser) {

        // 1️⃣ Find user by email
        UserModel user = userRepository.findByEmail(requestUser.getEmail());

        if (user == null) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // 2️⃣ 🚫 BLOCKED USER CHECK (NEW)
        if (user.isBlocked()) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Your account has been blocked by admin");
        }

        // 3️⃣ Generate token using existing logic
        String token = tokenGenerator.generateToken(
                requestUser.getEmail(),
                requestUser.getPassword()
        );

        if (token != null) {
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }

    
    @PostMapping("/update-password")
    public String updatePassword(@RequestBody PasswordRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return "Unauthorized";
        }

        CustomUserDetail userDetails = (CustomUserDetail) auth.getPrincipal();
        UserModel user = userDetails.getUser();

        boolean ok = customUserDetailsService.updatePassword(
                user,
                request.getOldPassword(),
                request.getNewPassword()
        );

        return ok ? "Password updated successfully" : "Old password incorrect";
    }
    
    @GetMapping("/user/me")
    public ResponseEntity<?> getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        CustomUserDetail userDetails = (CustomUserDetail) auth.getPrincipal();
        UserModel user = userDetails.getUser();

        return ResponseEntity.ok(Map.of(
            "fullname", user.getFullname(),
            "email", user.getEmail()
        ));
    }


}