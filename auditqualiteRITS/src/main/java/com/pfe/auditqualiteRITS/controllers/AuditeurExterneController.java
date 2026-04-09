package com.pfe.auditqualiteRITS.controllers;

import com.pfe.auditqualiteRITS.entite.AuditeurExterne;
import com.pfe.auditqualiteRITS.services.AuditeurExterneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditeurs-externes")
@RequiredArgsConstructor
public class AuditeurExterneController {

    private final AuditeurExterneService auditeurExterneService;

    @GetMapping
    public List<AuditeurExterne> getAll() {
        return auditeurExterneService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditeurExterne> getById(@PathVariable Long id) {
        AuditeurExterne auditeur = auditeurExterneService.findById(id);
        if (auditeur == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(auditeur);
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<AuditeurExterne> getByNom(@PathVariable String nom) {
        AuditeurExterne auditeur = auditeurExterneService.findByNom(nom);
        if (auditeur == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(auditeur);
    }

    @GetMapping("/statut/{statut}")
    public List<AuditeurExterne> getByStatut(@PathVariable String statut) {
        return auditeurExterneService.findByStatut(statut);
    }

    @PostMapping
    public ResponseEntity<AuditeurExterne> create(@RequestBody AuditeurExterne auditeurExterne) {
        AuditeurExterne saved = auditeurExterneService.create(auditeurExterne);
        if (saved == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuditeurExterne> update(@PathVariable Long id, @RequestBody AuditeurExterne updated) {
        AuditeurExterne result = auditeurExterneService.update(id, updated);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<AuditeurExterne> updateStatut(@PathVariable Long id, @RequestParam String statut) {
        AuditeurExterne result = auditeurExterneService.updateStatut(id, statut);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (auditeurExterneService.delete(id)) {
            return ResponseEntity.ok("External auditor deleted successfully");
        }
        return ResponseEntity.badRequest().body("Cannot delete external auditor with existing mission");
    }
}