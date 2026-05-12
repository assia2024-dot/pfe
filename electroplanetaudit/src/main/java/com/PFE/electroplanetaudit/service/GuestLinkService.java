package com.PFE.electroplanetaudit.service;

import com.PFE.electroplanetaudit.entity.*;
import com.PFE.electroplanetaudit.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class GuestLinkService {

    private final GuestLinkRepository guestLinkRepository;
    private final GuestAuditorRepository guestAuditorRepository;
    private final AuditMissionRepository auditMissionRepository;

    private static final SecureRandom random = new SecureRandom();

    // Generate 6-digit code
    private String generateCode() {
        return String.format("%06d", random.nextInt(1000000));
    }

    // Generate unique token
    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    // Create guest link for a mission (by ADMIN)
    public GuestLink createGuestLink(Long missionId, String guestNom, String guestTelephone, int expirationHours) {

        // Check if mission exists
        AuditMission mission = auditMissionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Mission not found"));

        // Check if mission already has an active guest link
        if (guestLinkRepository.existsByMissionIdAndUsedFalse(missionId)) {
            throw new RuntimeException("Mission already has an active guest link");
        }

        // Create guest auditor
        GuestAuditor guestAuditor = GuestAuditor.builder()
                .nom(guestNom)
                .telephone(guestTelephone)
                .statut("EN_ATTENTE")
                .dateCreation(LocalDateTime.now())
                .build();
        guestAuditor = guestAuditorRepository.save(guestAuditor);

        // Create guest link
        GuestLink guestLink = GuestLink.builder()
                .token(generateToken())
                .codeAcces(generateCode())
                .expiration(LocalDateTime.now().plusHours(expirationHours))
                .used(false)
                .mission(mission)
                .guestAuditor(guestAuditor)
                .dateCreation(LocalDateTime.now())
                .build();

        return guestLinkRepository.save(guestLink);
    }

    // Validate guest link (when guest clicks WhatsApp link)
    @Transactional(readOnly = true)
    public GuestLink validateLink(String token, String code) {
        GuestLink guestLink = guestLinkRepository.findByTokenAndCodeAcces(token, code)
                .orElseThrow(() -> new RuntimeException("Invalid link or code"));

        if (guestLink.getUsed()) {
            throw new RuntimeException("This link has already been used");
        }

        if (guestLink.getExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("This link has expired");
        }

        return guestLink;
    }

    // Mark link as used and update guest auditor status
    public GuestLink useLink(String token, String code) {
        GuestLink guestLink = validateLink(token, code);

        guestLink.setUsed(true);
        guestLink = guestLinkRepository.save(guestLink);

        // Update guest auditor status
        GuestAuditor guestAuditor = guestLink.getGuestAuditor();
        guestAuditor.setStatut("EN_COURS");
        guestAuditor.setDateAcces(LocalDateTime.now());
        guestAuditorRepository.save(guestAuditor);

        return guestLink;
    }

    // Get guest link by mission (for ADMIN)
    @Transactional(readOnly = true)
    public GuestLink getGuestLinkByMission(Long missionId) {
        return guestLinkRepository.findByMissionId(missionId).orElse(null);
    }

    // Get guest auditor by ID
    @Transactional(readOnly = true)
    public GuestAuditor getGuestAuditor(Long id) {
        return guestAuditorRepository.findById(id).orElse(null);
    }

    // Update guest auditor status after audit completion
    public void completeGuestAudit(Long guestLinkId) {
        GuestLink guestLink = guestLinkRepository.findById(guestLinkId)
                .orElseThrow(() -> new RuntimeException("Guest link not found"));

        GuestAuditor guestAuditor = guestLink.getGuestAuditor();
        guestAuditor.setStatut("TERMINE");
        guestAuditorRepository.save(guestAuditor);
    }
}