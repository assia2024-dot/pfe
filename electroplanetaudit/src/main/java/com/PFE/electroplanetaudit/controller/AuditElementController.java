package com.PFE.electroplanetaudit.controller;

import com.PFE.electroplanetaudit.entity.AuditElement;
import com.PFE.electroplanetaudit.service.AuditElementService;
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
@RequestMapping("/api/audit-elements")
@RequiredArgsConstructor
public class AuditElementController {

    private final AuditElementService auditElementService;

    // ===== CREATE - ADMIN ONLY =====
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody AuditElement element) {
        AuditElement created = auditElementService.create(element);
        return ResponseEntity.ok(created);
    }

    // ===== READ (All) - Both ADMIN and AUDITEUR =====
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AuditElement>> getAll() {
        return ResponseEntity.ok(auditElementService.findAll());
    }

    // ===== READ (By ID) - Both =====
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AuditElement> getById(@PathVariable Long id) {
        AuditElement element = auditElementService.findById(id);
        if (element == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(element);
    }

    // ===== READ (Active only) - Both =====
    @GetMapping("/active")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AuditElement>> getActiveElements() {
        return ResponseEntity.ok(auditElementService.findActiveElements());
    }

    // ===== READ (Search) - Both =====
    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AuditElement>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(auditElementService.search(keyword));
    }

    // ===== READ (Filtered with pagination) - Both =====
    @GetMapping("/filtered")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<AuditElement>> getFiltered(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean actif,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nom") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return ResponseEntity.ok(auditElementService.getElementsWithFilters(keyword, actif, pageable));
    }

    // ===== READ (Statistics for BI) - Both =====
    @GetMapping("/stats/actif-status")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Object[]>> getStatsByActifStatus() {
        return ResponseEntity.ok(auditElementService.getStatsByActifStatus());
    }

    // ===== UPDATE - ADMIN ONLY =====
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuditElement> update(@PathVariable Long id, @RequestBody AuditElement element) {
        AuditElement updated = auditElementService.update(id, element);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // ===== DELETE - ADMIN ONLY =====
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = auditElementService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"message\": \"Audit element deleted successfully\"}");
    }
}