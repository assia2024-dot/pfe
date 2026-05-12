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
    private static final int CODE_EXPIRATION_MINUTES = 5;

    // Generate 6-digit code and send to email
    @Transactional  // ← ADD THIS ANNOTATION
    public String generateAndSendCode(String email) {
        // Delete old codes for this email
        twoFACodeRepository.deleteByEmailAndUsedTrueOrExpirationBefore(email, LocalDateTime.now());

        // Generate 6-digit code (100000 to 999999)
        String code = String.format("%06d", random.nextInt(900000) + 100000);

        // Save to database
        TwoFACode twoFACode = TwoFACode.builder()
                .email(email)
                .code(code)
                .expiration(LocalDateTime.now().plusMinutes(CODE_EXPIRATION_MINUTES))
                .used(false)
                .build();
        twoFACodeRepository.save(twoFACode);

        // Send email
        emailService.sendTwoFACode(email, code);

        return code;
    }

    // Verify the 6-digit code
    @Transactional  // ← ADD THIS ANNOTATION
    public boolean verifyCode(String email, String code) {
        LocalDateTime now = LocalDateTime.now();

        return twoFACodeRepository
                .findByEmailAndCodeAndUsedFalseAndExpirationAfter(email, code, now)
                .map(twoFACode -> {
                    // Mark code as used
                    twoFACode.setUsed(true);
                    twoFACodeRepository.save(twoFACode);
                    return true;
                })
                .orElse(false);
    }
}