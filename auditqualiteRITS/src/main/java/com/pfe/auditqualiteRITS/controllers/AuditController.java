package com.pfe.auditqualiteRITS.controllers;

import com.pfe.auditqualiteRITS.entite.Audit;
import com.pfe.auditqualiteRITS.services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audits")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    public List<Audit> getAll() {
        return auditService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Audit> getById(@PathVariable Long id) {
        Audit audit = auditService.findById(id);
        if (audit == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(audit);
    }

    @GetMapping("/auditeur/{auditeurId}")
    public List<Audit> getByAuditeur(@PathVariable Long auditeurId) {
        return auditService.findByAuditeurId(auditeurId);
    }

    @GetMapping("/magasin/{magasinId}")
    public List<Audit> getByMagasin(@PathVariable Long magasinId) {
        return auditService.findByMagasinId(magasinId);
    }

    @GetMapping("/statut/{statut}")
    public List<Audit> getByStatut(@PathVariable String statut) {
        return auditService.findByStatut(statut);
    }

    @PostMapping
    public ResponseEntity<Audit> create(@RequestBody Audit audit) {
        Audit saved = auditService.create(audit);
        if (saved == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Audit> update(@PathVariable Long id, @RequestBody Audit updated) {
        Audit result = auditService.update(id, updated);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<Audit> updateStatut(@PathVariable Long id, @RequestParam String statut) {
        Audit result = auditService.updateStatut(id, statut);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/note")
    public ResponseEntity<Audit> updateNoteGlobale(@PathVariable Long id, @RequestParam Double noteGlobale) {
        Audit result = auditService.updateNoteGlobale(id, noteGlobale);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (auditService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}