package com.pfe.auditqualiteRITS.controllers;

import com.pfe.auditqualiteRITS.entite.Utilisateur;
import com.pfe.auditqualiteRITS.services.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @GetMapping
    public List<Utilisateur> getAll() {
        return utilisateurService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getById(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.findById(id);
        if (utilisateur == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(utilisateur);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Utilisateur> getByEmail(@PathVariable String email) {
        Utilisateur utilisateur = utilisateurService.findByEmail(email);
        if (utilisateur == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(utilisateur);
    }

    @PostMapping
    public ResponseEntity<Utilisateur> create(@RequestBody Utilisateur utilisateur) {
        Utilisateur saved = utilisateurService.create(utilisateur);
        if (saved == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> update(@PathVariable Long id, @RequestBody Utilisateur updated) {
        Utilisateur result = utilisateurService.update(id, updated);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (utilisateurService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}