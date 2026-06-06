package com.example.demo.repository;

import com.example.demo.model.Userdetails;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserdetailsRepository extends JpaRepository<Userdetails, Long> {

    boolean existsByEmail(String email);
    Userdetails findByEmail(String email);
    Optional<Userdetails> findByGoogleId(String googleId);
    Optional<Userdetails> findByResetToken(String resetToken);
}