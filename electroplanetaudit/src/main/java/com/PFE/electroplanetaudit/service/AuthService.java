package com.PFE.electroplanetaudit.service;

import com.PFE.electroplanetaudit.entity.User;
import com.PFE.electroplanetaudit.repository.UserRepository;
import com.PFE.electroplanetaudit.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TwoFAService twoFAService;

    @Autowired
    private JwtUtil jwtUtil;

    // Step 1: Validate credentials, send 2FA code
    public boolean validateCredentialsAndSendCode(String email, String rawPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return false; // User not found
        }

        User user = userOpt.get();

        if (!user.getActif()) {
            return false; // User is disabled
        }

        // Check password (using BCrypt)
        if (!passwordEncoder.matches(rawPassword, user.getMotDePasse())) {
            return false; // Wrong password
        }

        // Generate and send 2FA code
        twoFAService.generateAndSendCode(email);

        return true;
    }

    // Step 2: Verify 2FA code and generate JWT
    public String verify2FAAndGenerateToken(String email, String code) {
        // Verify the 2FA code
        boolean isValid = twoFAService.verifyCode(email, code);

        if (!isValid) {
            return null; // Invalid or expired code
        }

        // Get user details
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return null;
        }

        // Generate JWT token
        return jwtUtil.generateToken(user.getEmail(), user.getId(), user.getRole().name());
    }
}