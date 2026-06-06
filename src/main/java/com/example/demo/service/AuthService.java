package com.example.demo.service;

import com.example.demo.model.Userdetails;
import com.example.demo.repository.UserdetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserdetailsRepository Userdetailsrepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // ← ADD THIS

    public Userdetails login(String email, String password) {
        Userdetails user = Userdetailsrepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("Invalid credentials");
        }

        // Handle old plain text passwords (your existing users)
        if (!user.getPassword().startsWith("$2a$")) {
            if (user.getPassword().equals(password)) {
                // Auto upgrade to encrypted
                user.setPassword(passwordEncoder.encode(password));
                Userdetailsrepository.save(user);
                return user;
            }
            throw new RuntimeException("Invalid credentials");
        }

        // BCrypt check for new users
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        throw new RuntimeException("Invalid credentials");
    }

    public Userdetails register(Userdetails userdetails) {

        // Check duplicate email
        if (Userdetailsrepository.existsByEmail(userdetails.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Encrypt password before saving ← ADD THIS
        userdetails.setPassword(passwordEncoder.encode(userdetails.getPassword()));

        return Userdetailsrepository.save(userdetails);
    }
}