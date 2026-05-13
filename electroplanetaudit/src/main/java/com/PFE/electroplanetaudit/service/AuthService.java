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

    public boolean validateCredentialsAndSendCode(String email, String rawPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();

        if (!user.getActif()) {
            return false;
        }

        if (!passwordEncoder.matches(rawPassword, user.getMotDePasse())) {
            return false;
        }

        // Use login-specific method
        twoFAService.generateAndSendLoginCode(email);

        return true;
    }

    public String verify2FAAndGenerateToken(String email, String code) {
        // Use login-specific verify
        boolean isValid = twoFAService.verifyLoginCode(email, code);

        if (!isValid) {
            return null;
        }

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return null;
        }

        return jwtUtil.generateToken(user.getEmail(), user.getId(), user.getRole().name());
    }

}