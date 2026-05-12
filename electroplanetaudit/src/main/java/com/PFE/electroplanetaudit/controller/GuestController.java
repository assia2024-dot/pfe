package com.PFE.electroplanetaudit.controller;

import com.PFE.electroplanetaudit.entity.GuestLink;
import com.PFE.electroplanetaudit.service.GuestLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/guest")
@RequiredArgsConstructor
public class GuestController {

    private final GuestLinkService guestLinkService;

    // ===== ADMIN: Create guest link for a mission =====
    @PostMapping("/links")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createGuestLink(@RequestBody Map<String, Object> request) {
        Long missionId = Long.valueOf(request.get("missionId").toString());
        String guestNom = (String) request.get("guestNom");
        String guestTelephone = (String) request.get("guestTelephone");
        int expirationHours = request.containsKey("expirationHours") ?
                (Integer) request.get("expirationHours") : 24;  // Default 24 hours

        GuestLink guestLink = guestLinkService.createGuestLink(missionId, guestNom, guestTelephone, expirationHours);

        // Generate WhatsApp link
        String baseUrl = "http://localhost:4200/guest-audit";  // Frontend URL
        String whatsappLink = "https://wa.me/" + guestTelephone + "?text=" +
                java.net.URLEncoder.encode("Bonjour! Voici votre lien d'audit: " +
                        baseUrl + "?token=" + guestLink.getToken() + "&code=" + guestLink.getCodeAcces());

        return ResponseEntity.ok(Map.of(
                "guestLink", guestLink,
                "whatsappLink", whatsappLink
        ));
    }

    // ===== GUEST: Validate link (called when guest opens the link) =====
    @GetMapping("/validate")
    public ResponseEntity<?> validateLink(@RequestParam String token, @RequestParam String code) {
        try {
            GuestLink guestLink = guestLinkService.validateLink(token, code);
            return ResponseEntity.ok(Map.of(
                    "valid", true,
                    "missionId", guestLink.getMission().getId(),
                    "storeId", guestLink.getMission().getStore().getId(),
                    "storeName", guestLink.getMission().getStore().getNom(),
                    "guestLinkId", guestLink.getId()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("valid", false, "message", e.getMessage()));
        }
    }

    // ===== GUEST: Use link (after validation, start audit) =====
    @PostMapping("/use")
    public ResponseEntity<?> useLink(@RequestParam String token, @RequestParam String code) {
        try {
            GuestLink guestLink = guestLinkService.useLink(token, code);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Link activated. You can now start the audit.",
                    "missionId", guestLink.getMission().getId()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    // ===== ADMIN: Get guest link by mission =====
    @GetMapping("/links/mission/{missionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getGuestLinkByMission(@PathVariable Long missionId) {
        GuestLink guestLink = guestLinkService.getGuestLinkByMission(missionId);
        if (guestLink == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(guestLink);
    }
}