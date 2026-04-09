package com.pfe.auditqualiteRITS.controllers;

import com.pfe.auditqualiteRITS.entite.Auditeur;
import com.pfe.auditqualiteRITS.services.AuditeurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditeurs")
@RequiredArgsConstructor
public class AuditeurController {

    private final AuditeurService auditeurService;

    @GetMapping
    public List<Auditeur> getAll() {
        return auditeurService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auditeur> getById(@PathVariable Long id) {
        Auditeur auditeur = auditeurService.findById(id);
        if (auditeur == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(auditeur);
    }

    @GetMapping("/region/{region}")
    public List<Auditeur> getByRegion(@PathVariable String region) {
        return auditeurService.findByRegion(region);
    }

    @PostMapping
    public ResponseEntity<Auditeur> create(@RequestBody Auditeur auditeur) {
        Auditeur saved = auditeurService.create(auditeur);
        if (saved == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auditeur> update(@PathVariable Long id, @RequestBody Auditeur updated) {
        Auditeur result = auditeurService.update(id, updated);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (auditeurService.delete(id)) {
            return ResponseEntity.ok("Auditor deleted successfully");
        }
        return ResponseEntity.badRequest().body("Cannot delete auditor with existing missions or audits");
    }
}