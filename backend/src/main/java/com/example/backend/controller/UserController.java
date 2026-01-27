package com.example.backend.controller;

import com.example.backend.models.UserModel;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.TokenGenerator;
import java.util.Map;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.backend.dto.PasswordRequest;
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
    public ResponseEntity<?> login(@RequestBody UserModel user) {
        String token = tokenGenerator.generateToken(user.getEmail(), user.getPassword());
        if (token != null) {
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
    
    @PostMapping("/update-password")
    public String updatePassword(@RequestBody PasswordRequest request) {

        org.springframework.security.core.@Nullable Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService userDetails = (CustomUserDetailsService) auth.getPrincipal();
        UserModel user = userDetails.getUser();

        boolean ok = customUserDetailsService.updatePassword(
                user, request.getOldPassword(), request.getNewPassword());

        return ok ? "Password updated successfully" : "Old password incorrect";
    }

}