package com.example.demo.controller;

import com.example.demo.model.Userdetails;
import com.example.demo.repository.UserdetailsRepository;
import com.example.demo.service.JwtService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class GoogleAuthController {

 @Value("${google.client-id}")
 private String clientId;

 @Autowired
 private UserdetailsRepository userRepository;

 @Autowired
 private JwtService jwtService;

 @PostMapping("/google")
 public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> body) {
     try {
         String idTokenString = body.get("idToken");

         // Step 1: Verify the token with Google
         GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                 new NetHttpTransport(), GsonFactory.getDefaultInstance())
                 .setAudience(Collections.singletonList(clientId))
                 .build();

         GoogleIdToken idToken = verifier.verify(idTokenString);

         if (idToken == null) {
             return ResponseEntity.status(401)
                 .body(Map.of("error", "Invalid Google token"));
         }

         // Step 2: Extract user info from token
         GoogleIdToken.Payload payload = idToken.getPayload();
         String googleId = payload.getSubject();
         String email    = payload.getEmail();

         // Step 3: Check if user exists, if not create them
         Userdetails user = userRepository.findByGoogleId(googleId)
                 .orElseGet(() -> {
                	 Userdetails newUser = new Userdetails();
                     newUser.setGoogleId(googleId);
                     newUser.setEmail(email); 
                     newUser.setPassword("");
                     return userRepository.save(newUser);
                 });

         // Step 4: Generate your own JWT
         String jwt = jwtService.generateToken(user);

         return ResponseEntity.ok(Map.of(
             "token", jwt,
             "email", user.getEmail()
         ));

     } catch (Exception e) {
         return ResponseEntity.status(500)
             .body(Map.of("error", "Authentication failed: " + e.getMessage()));
     }
 }
}
