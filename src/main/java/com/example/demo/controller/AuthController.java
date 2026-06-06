package com.example.demo.controller;

import com.example.demo.model.Userdetails;
import com.example.demo.service.AuthService;
import com.example.demo.service.JwtService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService; // ← ADD THIS

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Userdetails user) {
        Map<String, Object> response = new HashMap<>();
        try {
            Userdetails loggedIn = authService.login(
                user.getEmail(),
                user.getPassword()
            );

            // Generate JWT token
            String token = jwtService.generateToken(loggedIn);

            response.put("success", true);
            response.put("token", token);
            response.put("email", loggedIn.getEmail());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(
            @RequestBody Userdetails userdetails) {

        Map<String, Object> response = new HashMap<>();
        try {
            Userdetails saved = authService.register(userdetails);
            response.put("success", true);
            response.put("message", "Registered successfully");
            response.put("phone", saved.getPhoneNumber());
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}