package com.PFE.electroplanetaudit.controller;

import com.PFE.electroplanetaudit.entity.MediaEvidence;
import com.PFE.electroplanetaudit.service.MediaEvidenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaEvidenceController {

    private final MediaEvidenceService mediaEvidenceService;

    // ===== UPLOAD PHOTO (AUDITEUR ONLY) =====
    @PostMapping(value = "/upload/{sessionId}/{elementId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('AUDITEUR')")
    public ResponseEntity<?> uploadPhoto(@PathVariable Long sessionId,
                                         @PathVariable Long elementId,
                                         @RequestParam("file") MultipartFile file) {
        try {
            MediaEvidence media = mediaEvidenceService.uploadPhoto(sessionId, elementId, file);
            return ResponseEntity.ok(media);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }

    // ===== GET ALL PHOTOS FOR A SESSION (AUDITEUR + ADMIN) =====
    @GetMapping("/session/{sessionId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MediaEvidence>> getPhotosBySession(@PathVariable Long sessionId) {
        return ResponseEntity.ok(mediaEvidenceService.getPhotosBySession(sessionId));
    }

    // ===== GET PHOTOS FOR A SPECIFIC ELEMENT (AUDITEUR + ADMIN) =====
    @GetMapping("/element/{sessionId}/{elementId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MediaEvidence>> getPhotosByElement(@PathVariable Long sessionId,
                                                                  @PathVariable Long elementId) {
        return ResponseEntity.ok(mediaEvidenceService.getPhotosByElement(sessionId, elementId));
    }

    // ===== DELETE PHOTO (AUDITEUR + ADMIN) =====
    @DeleteMapping("/{mediaId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deletePhoto(@PathVariable Long mediaId) {
        boolean deleted = mediaEvidenceService.deletePhoto(mediaId);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"message\": \"Photo deleted successfully\"}");
    }
}