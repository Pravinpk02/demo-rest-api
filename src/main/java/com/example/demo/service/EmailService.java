package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        String resetLink = frontendUrl + "/reset-password?token=" + resetToken;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Bischen — Reset Your Password");
        message.setText(
            "Hi,\n\n" +
            "You requested to reset your password.\n\n" +
            "Click the link below to reset it:\n\n" +
            resetLink + "\n\n" +
            "This link expires in 30 minutes.\n\n" +
            "If you didn't request this, please ignore this email.\n\n" +
            "— Bischen Team"
        );

        mailSender.send(message);
    }
}
