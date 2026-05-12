package com.PFE.electroplanetaudit.controller;

import com.PFE.electroplanetaudit.dto.*;
import com.PFE.electroplanetaudit.entity.Role;
import com.PFE.electroplanetaudit.entity.User;
import com.PFE.electroplanetaudit.repository.UserRepository;
import com.PFE.electroplanetaudit.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    // Step 1: Login - Validate credentials and send 2FA code
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        boolean isValid = authService.validateCredentialsAndSendCode(
                request.getEmail(),
                request.getPassword()
        );

        if (!isValid) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Invalid email or password"));
        }

        return ResponseEntity.ok()
                .body(new MessageResponse("2FA code sent to your email"));
    }

    // Step 2: Verify 2FA code and get JWT token
    @PostMapping("/verify-2fa")
    public ResponseEntity<?> verify2FA(@Valid @RequestBody TwoFAVerifyRequest request) {
        String token = authService.verify2FAAndGenerateToken(
                request.getEmail(),
                request.getCode()
        );

        if (token == null) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Invalid or expired 2FA code"));
        }

        // Get user details for response
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("User not found"));
        }

        AuthResponse response = new AuthResponse(
                token,
                user.getId(),
                user.getUserCode(),
                user.getNom(),
                user.getPrenom(),
                user.getEmail(),
                user.getRole().name()
        );

        return ResponseEntity.ok(response);
    }

    // Test endpoint (requires authentication)
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(new MessageResponse("You are authenticated!"));
    }


}