package com.PFE.electroplanetaudit.service;

import com.PFE.electroplanetaudit.entity.TwoFACode;
import com.PFE.electroplanetaudit.repository.TwoFACodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class TwoFAService {

    @Autowired
    private TwoFACodeRepository twoFACodeRepository;

    @Autowired
    private EmailService emailService;

    private static final SecureRandom random = new SecureRandom();
    private static final int CODE_EXPIRATION_MINUTES = 15;
    private static final int RESEND_COOLDOWN_SECONDS = 60;

    // Generate and send code for LOGIN
    @Transactional
    public String generateAndSendLoginCode(String email) {
        return generateAndSendCode(email, "LOGIN");
    }

    // Generate and send code for RESET PASSWORD
    @Transactional
    public String generateAndSendResetCode(String email) {
        return generateAndSendCode(email, "RESET_PASSWORD");
    }

    @Transactional
    public String resendLoginCode(String email) {
        // Check cooldown: Can't resend within 60 seconds
        LocalDateTime oneMinuteAgo = LocalDateTime.now().minusSeconds(RESEND_COOLDOWN_SECONDS);

        TwoFACode recentCode = twoFACodeRepository
                .findTopByEmailAndTypeAndCreationTimeAfterOrderByCreationTimeDesc(email, "LOGIN", oneMinuteAgo);

        if (recentCode != null) {
            throw new RuntimeException("Please wait " + RESEND_COOLDOWN_SECONDS + " seconds before requesting another code.");
        }

        return generateAndSendCode(email, "LOGIN");
    }

    @Transactional
    public String resendResetCode(String email) {
        // Check cooldown
        LocalDateTime oneMinuteAgo = LocalDateTime.now().minusSeconds(RESEND_COOLDOWN_SECONDS);

        TwoFACode recentCode = twoFACodeRepository
                .findTopByEmailAndTypeAndCreationTimeAfterOrderByCreationTimeDesc(email, "RESET_PASSWORD", oneMinuteAgo);

        if (recentCode != null) {
            throw new RuntimeException("Please wait " + RESEND_COOLDOWN_SECONDS + " seconds before requesting another code.");
        }

        return generateAndSendCode(email, "RESET_PASSWORD");
    }

    // Generic method to generate and send code
    private String generateAndSendCode(String email, String type) {
        // Delete old codes for this email and type
        twoFACodeRepository.deleteOldCodes(email, type, LocalDateTime.now());

        // Generate 6-digit code (100000 to 999999)
        String code = String.format("%06d", random.nextInt(900000) + 100000);

        // Save to database
        TwoFACode twoFACode = TwoFACode.builder()
                .email(email)
                .code(code)
                .type(type)
                .expiration(LocalDateTime.now().plusMinutes(CODE_EXPIRATION_MINUTES))
                .used(false)
                .creationTime(LocalDateTime.now())
                .build();
        twoFACodeRepository.save(twoFACode);

        // Send email based on type
        if ("LOGIN".equals(type)) {
            emailService.sendTwoFACode(email, code);
        } else {
            emailService.sendPasswordResetCode(email, code);
        }

        return code;
    }

    // Verify code for LOGIN
    @Transactional
    public boolean verifyLoginCode(String email, String code) {
        return verifyCode(email, code, "LOGIN");
    }

    // Verify code for RESET PASSWORD
    @Transactional
    public boolean verifyResetCode(String email, String code) {
        return verifyCode(email, code, "RESET_PASSWORD");
    }

    // Generic verify method
    private boolean verifyCode(String email, String code, String type) {
        LocalDateTime now = LocalDateTime.now();

        return twoFACodeRepository
                .findByEmailAndCodeAndTypeAndUsedFalseAndExpirationAfter(email, code, type, now)
                .map(twoFACode -> {
                    // Mark code as used
                    twoFACode.setUsed(true);
                    twoFACodeRepository.save(twoFACode);
                    return true;
                })
                .orElse(false);
    }


    // Verify reset code WITHOUT marking it as used (just check validity)
    @Transactional
    public boolean verifyResetCodeOnly(String email, String code) {
        LocalDateTime now = LocalDateTime.now();
        return twoFACodeRepository
                .findByEmailAndCodeAndTypeAndUsedFalseAndExpirationAfter(email, code, "RESET_PASSWORD", now)
                .isPresent();
    }
}