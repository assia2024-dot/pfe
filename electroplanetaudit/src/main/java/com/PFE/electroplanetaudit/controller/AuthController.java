package com.PFE.electroplanetaudit.controller;

import com.PFE.electroplanetaudit.dto.*;
import com.PFE.electroplanetaudit.entity.User;
import com.PFE.electroplanetaudit.repository.UserRepository;
import com.PFE.electroplanetaudit.service.AuthService;
import com.PFE.electroplanetaudit.service.TwoFAService;
import com.PFE.electroplanetaudit.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final TwoFAService twoFAService;
    private final UserService userService;

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

    // Step 1: Forgot password - send reset code
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        try {
            if (!userService.userExistsByEmail(request.getEmail())) {
                return ResponseEntity.ok().body(new MessageResponse("If your email is registered, you will receive a reset code."));
            }

            twoFAService.generateAndSendResetCode(request.getEmail());
            return ResponseEntity.ok().body(new MessageResponse("Reset code sent to your email."));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    // Step 2: Verify reset code
    @PostMapping("/verify-reset-code")
    public ResponseEntity<?> verifyResetCode(@Valid @RequestBody VerifyResetCodeRequest request) {
        boolean isValid = twoFAService.verifyResetCode(request.getEmail(), request.getCode());

        if (!isValid) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid or expired code."));
        }

        return ResponseEntity.ok().body(new MessageResponse("Code verified successfully."));
    }

    // Step 3: Reset password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        boolean reset = userService.resetPassword(request.getEmail(), request.getCode(), request.getNewPassword());

        if (!reset) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid or expired code."));
        }

        return ResponseEntity.ok().body(new MessageResponse("Password reset successfully. You can now login with your new password."));
    }

    // Test endpoint (requires authentication)
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(new MessageResponse("You are authenticated!"));
    }
}