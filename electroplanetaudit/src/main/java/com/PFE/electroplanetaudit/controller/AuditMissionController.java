package com.PFE.electroplanetaudit.controller;

import com.PFE.electroplanetaudit.entity.AuditMission;
import com.PFE.electroplanetaudit.entity.MissionStatus;
import com.PFE.electroplanetaudit.service.AuditMissionService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/audit-missions")
@RequiredArgsConstructor
public class AuditMissionController {

    private final AuditMissionService auditMissionService;

    // ===== CREATE - ADMIN ONLY =====
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody AuditMission mission,
                                    @RequestParam(required = false) List<Long> elementIds) {
        AuditMission created = auditMissionService.create(mission, elementIds, null);
        return ResponseEntity.ok(created);
    }

    // ===== READ (All) - ADMIN ONLY =====
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuditMission>> getAll() {
        return ResponseEntity.ok(auditMissionService.findAll());
    }

    // ===== READ (By ID) - ADMIN ONLY =====
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuditMission> getById(@PathVariable Long id) {
        AuditMission mission = auditMissionService.findById(id);
        if (mission == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mission);
    }

    // ===== READ (By auditor userCode) - ADMIN ONLY =====
    @GetMapping("/auditeur/{userCode}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuditMission>> getByAuditeur(@PathVariable Integer userCode) {
        return ResponseEntity.ok(auditMissionService.findByAuditeurCode(userCode));
    }

    // ===== READ (By store) - ADMIN ONLY =====
    @GetMapping("/store/{storeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuditMission>> getByStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(auditMissionService.findByStore(storeId));
    }

    // ===== READ (By status) - ADMIN ONLY =====
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuditMission>> getByStatus(@PathVariable String status) {
        MissionStatus missionStatus = MissionStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(auditMissionService.findByStatus(missionStatus));
    }

    // ===== READ (Search) - ADMIN ONLY =====
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuditMission>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(auditMissionService.search(keyword));
    }

    // ===== READ (Filtered with pagination) - ADMIN ONLY =====
    @GetMapping("/filtered")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AuditMission>> getFiltered(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long storeId,
            @RequestParam(required = false) Long auditeurId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDebut") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        MissionStatus missionStatus = null;
        if (status != null && !status.isEmpty()) {
            missionStatus = MissionStatus.valueOf(status.toUpperCase());
        }

        return ResponseEntity.ok(auditMissionService.getMissionsWithFilters(keyword, missionStatus, storeId, auditeurId, pageable));
    }

    // ===== READ (Missions for logged-in auditor) - AUDITEUR ONLY =====
    @GetMapping("/auditeur/me/{userCode}")
    @PreAuthorize("hasRole('AUDITEUR')")
    public ResponseEntity<Page<AuditMission>> getMyMissions(
            @PathVariable Integer userCode,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDebut") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        MissionStatus missionStatus = null;
        if (status != null && !status.isEmpty()) {
            missionStatus = MissionStatus.valueOf(status.toUpperCase());
        }

        return ResponseEntity.ok(auditMissionService.getMissionsByAuditeurCode(userCode, missionStatus, pageable));
    }

    // ===== READ (Statistics for BI) - ADMIN ONLY =====
    @GetMapping("/stats/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Object[]>> getStatsByStatus() {
        return ResponseEntity.ok(auditMissionService.getStatsByStatus());
    }

    @GetMapping("/stats/store")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Object[]>> getStatsByStore() {
        return ResponseEntity.ok(auditMissionService.getStatsByStore());
    }

    @GetMapping("/stats/auditeur")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Object[]>> getStatsByAuditeur() {
        return ResponseEntity.ok(auditMissionService.getStatsByAuditeur());
    }

    @GetMapping("/upcoming")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuditMission>> getUpcoming() {
        return ResponseEntity.ok(auditMissionService.getUpcomingMissions());
    }

    // ===== UPDATE - Full update (ADMIN ONLY) - includes status =====
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuditMission> update(@PathVariable Long id,
                                               @RequestBody AuditMission mission,
                                               @RequestParam(required = false) List<Long> elementIds) {
        AuditMission updated = auditMissionService.update(id, mission, elementIds);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // ===== DELETE - ADMIN ONLY =====
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = auditMissionService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"message\": \"Mission deleted successfully\"}");
    }
}