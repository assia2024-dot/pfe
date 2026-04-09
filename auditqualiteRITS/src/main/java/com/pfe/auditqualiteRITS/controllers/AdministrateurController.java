package com.pfe.auditqualiteRITS.controllers;

import com.pfe.auditqualiteRITS.entite.Administrateur;
import com.pfe.auditqualiteRITS.services.AdministrateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrateurs")
@RequiredArgsConstructor
public class AdministrateurController {

    private final AdministrateurService administrateurService;

    @GetMapping
    public List<Administrateur> getAll() {
        return administrateurService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrateur> getById(@PathVariable Long id) {
        Administrateur administrateur = administrateurService.findById(id);
        if (administrateur == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(administrateur);
    }

    @PostMapping
    public ResponseEntity<Administrateur> create(@RequestBody Administrateur administrateur) {
        Administrateur saved = administrateurService.create(administrateur);
        if (saved == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrateur> update(@PathVariable Long id, @RequestBody Administrateur updated) {
        Administrateur result = administrateurService.update(id, updated);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/actif")
    public ResponseEntity<Administrateur> updateActif(@PathVariable Long id, @RequestParam Boolean actif) {
        Administrateur result = administrateurService.updateActif(id, actif);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (administrateurService.delete(id)) {
            return ResponseEntity.ok("Administrator deleted successfully");
        }
        return ResponseEntity.badRequest().body("Cannot delete administrator with existing missions or external links");
    }
}