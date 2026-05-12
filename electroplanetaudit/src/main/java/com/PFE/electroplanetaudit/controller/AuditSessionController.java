package com.PFE.electroplanetaudit.controller;

import com.PFE.electroplanetaudit.dto.GradeRequest;
import com.PFE.electroplanetaudit.entity.AuditSession;
import com.PFE.electroplanetaudit.entity.ElementScore;
import com.PFE.electroplanetaudit.entity.SessionStatus;
import com.PFE.electroplanetaudit.service.AuditSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/audit-sessions")
@RequiredArgsConstructor
public class AuditSessionController {

    private final AuditSessionService auditSessionService;

    // ===== CREATE - Start audit from mission (AUDITEUR ONLY) =====
    @PostMapping("/start-from-mission")
    @PreAuthorize("hasRole('AUDITEUR')")
    public ResponseEntity<?> startFromMission(@RequestParam Long missionId,
                                              @RequestParam Long auditeurId) {
        // Check if mission can be started
        if (!auditSessionService.canStartMission(missionId, auditeurId)) {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"Mission cannot be started. Check date or status.\"}");
        }

        AuditSession session = auditSessionService.startFromMission(missionId, auditeurId);
        return ResponseEntity.ok(session);
    }

    // ===== READ (All) - ADMIN ONLY =====
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuditSession>> getAll() {
        return ResponseEntity.ok(auditSessionService.findAll());
    }

    // ===== READ (By ID) - ADMIN ONLY =====
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuditSession> getById(@PathVariable Long id) {
        AuditSession session = auditSessionService.findById(id);
        if (session == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(session);
    }

    // ===== READ (By store) - ADMIN ONLY =====
    @GetMapping("/store/{storeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuditSession>> getByStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(auditSessionService.findByStore(storeId));
    }

    // ===== READ (By auditor) - ADMIN ONLY =====
    @GetMapping("/auditeur/{auditeurId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuditSession>> getByAuditeur(@PathVariable Long auditeurId) {
        return ResponseEntity.ok(auditSessionService.findByAuditeur(auditeurId));
    }

    // ===== READ (My sessions) - AUDITEUR ONLY =====
    @GetMapping("/me/{auditeurId}")
    @PreAuthorize("hasRole('AUDITEUR')")
    public ResponseEntity<Page<AuditSession>> getMySessions(
            @PathVariable Long auditeurId,
            @RequestParam(required = false) String statut,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDebut") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        SessionStatus sessionStatus = null;
        if (statut != null && !statut.isEmpty()) {
            sessionStatus = SessionStatus.valueOf(statut.toUpperCase());
        }

        return ResponseEntity.ok(auditSessionService.getMySessions(auditeurId, sessionStatus, pageable));
    }

    // ===== READ (Filtered) - ADMIN ONLY =====
    @GetMapping("/filtered")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AuditSession>> getFiltered(
            @RequestParam(required = false) Long storeId,
            @RequestParam(required = false) Long auditeurId,
            @RequestParam(required = false) String statut,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDebut") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        SessionStatus sessionStatus = null;
        if (statut != null && !statut.isEmpty()) {
            sessionStatus = SessionStatus.valueOf(statut.toUpperCase());
        }

        return ResponseEntity.ok(auditSessionService.getSessionsWithFilters(storeId, auditeurId, sessionStatus, pageable));
    }

    // ===== READ (Scores by session) - Both =====
    @GetMapping("/{sessionId}/scores")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ElementScore>> getScoresBySession(@PathVariable Long sessionId) {
        return ResponseEntity.ok(auditSessionService.getScoresBySession(sessionId));
    }

    // ===== READ (Check if mission can be started) - AUDITEUR ONLY =====
    @GetMapping("/can-start/{missionId}/{auditeurId}")
    @PreAuthorize("hasRole('AUDITEUR')")
    public ResponseEntity<?> canStartMission(@PathVariable Long missionId, @PathVariable Long auditeurId) {
        boolean canStart = auditSessionService.canStartMission(missionId, auditeurId);
        return ResponseEntity.ok().body("{\"canStart\": " + canStart + "}");
    }

    // ===== UPDATE - Grade one element (AUDITEUR ONLY) =====
    @PutMapping("/{sessionId}/grade")
    @PreAuthorize("hasRole('AUDITEUR')")
    public ResponseEntity<ElementScore> gradeElement(@PathVariable Long sessionId,
                                                     @RequestParam Long elementId,
                                                     @RequestParam Integer score,
                                                     @RequestParam(required = false) String commentaire) {
        ElementScore graded = auditSessionService.gradeElement(sessionId, elementId, score, commentaire);
        return ResponseEntity.ok(graded);
    }

    // ===== UPDATE - Grade multiple elements (AUDITEUR ONLY) =====
    @PutMapping("/{sessionId}/grade-batch")
    @PreAuthorize("hasRole('AUDITEUR')")
    public ResponseEntity<?> gradeElements(@PathVariable Long sessionId,
                                           @RequestBody List<GradeRequest> grades) {
        auditSessionService.gradeElements(sessionId, grades);
        return ResponseEntity.ok().body("{\"message\": \"Scores saved successfully\"}");
    }

    // ===== UPDATE - Submit audit (AUDITEUR ONLY) =====
    @PatchMapping("/{sessionId}/submit")
    @PreAuthorize("hasRole('AUDITEUR')")
    public ResponseEntity<AuditSession> submitAudit(@PathVariable Long sessionId) {
        AuditSession session = auditSessionService.submitAudit(sessionId);
        return ResponseEntity.ok(session);
    }

    // ===== DELETE - ADMIN ONLY =====
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = auditSessionService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"message\": \"Audit session deleted successfully\"}");
    }

    // ===== STATISTICS FOR BI - ADMIN ONLY =====
    @GetMapping("/stats/average-by-store")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Object[]>> getAverageScoreByStore() {
        return ResponseEntity.ok(auditSessionService.getAverageScoreByStore());
    }

    @GetMapping("/stats/average-by-auditeur")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Object[]>> getAverageScoreByAuditeur() {
        return ResponseEntity.ok(auditSessionService.getAverageScoreByAuditeur());
    }

    @GetMapping("/stats/average-by-region")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Object[]>> getAverageScoreByRegion() {
        return ResponseEntity.ok(auditSessionService.getAverageScoreByRegion());
    }

    @GetMapping("/stats/monthly-count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Object[]>> getMonthlyAuditCount() {
        return ResponseEntity.ok(auditSessionService.getMonthlyAuditCount());
    }

    @GetMapping("/stats/element-performance")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Object[]>> getElementPerformanceSummary() {
        return ResponseEntity.ok(auditSessionService.getElementPerformanceSummary());
    }
}