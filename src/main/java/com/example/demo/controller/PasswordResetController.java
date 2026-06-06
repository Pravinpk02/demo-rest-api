package com.example.demo.controller;

import com.example.demo.model.Userdetails;
import com.example.demo.repository.UserdetailsRepository;
import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class PasswordResetController {

    @Autowired
    private UserdetailsRepository userRepository;

    @Autowired
    private EmailService emailService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ─── STEP 1: User submits their email ───────────────────────────
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            Userdetails user = userRepository.findByEmail(email);

            // Always return same message — security best practice
            // (don't reveal if email exists or not)
            if (user == null) {
                return ResponseEntity.ok(Map.of(
                    "message", "If this email exists, a reset link has been sent."
                ));
            }

            // Generate unique token
            String resetToken = UUID.randomUUID().toString();

            // Save token + 30 min expiry to DB
            user.setResetToken(resetToken);
            user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(30));
            userRepository.save(user);

            // Send email
            emailService.sendPasswordResetEmail(email, resetToken);

            return ResponseEntity.ok(Map.of(
                "message", "If this email exists, a reset link has been sent."
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(Map.of("error", "Something went wrong: " + e.getMessage()));
        }
    }

    // ─── STEP 2: User submits new password ──────────────────────────
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        try {
            String token       = body.get("token");
            String newPassword = body.get("password");

            // Find user by token
            Userdetails user = userRepository.findByResetToken(token)
                .orElse(null);

            // Token not found
            if (user == null) {
                return ResponseEntity.status(400)
                    .body(Map.of("error", "Invalid reset link."));
            }

            // Token expired
            if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
                return ResponseEntity.status(400)
                    .body(Map.of("error", "Reset link expired. Please request a new one."));
            }

            // Update password with encryption
            user.setPassword(passwordEncoder.encode(newPassword));

            // Clear token so it can't be reused
            user.setResetToken(null);
            user.setResetTokenExpiry(null);
            userRepository.save(user);

            return ResponseEntity.ok(Map.of(
                "message", "Password reset successful! You can now log in."
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(Map.of("error", "Something went wrong: " + e.getMessage()));
        }
    }
}
