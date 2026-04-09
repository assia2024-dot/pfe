package com.pfe.auditqualiteRITS.controllers;

import com.pfe.auditqualiteRITS.entite.LienExterne;
import com.pfe.auditqualiteRITS.services.LienExterneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/liens-externes")
@RequiredArgsConstructor
public class LienExterneController {

    private final LienExterneService lienExterneService;

    @GetMapping
    public List<LienExterne> getAllLiens() {
        return lienExterneService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LienExterne> getLienById(@PathVariable Long id) {
        LienExterne lien = lienExterneService.findById(id);
        if (lien == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lien);
    }

    @GetMapping("/code/{codeAcces}")
    public ResponseEntity<LienExterne> getLienByCodeAcces(@PathVariable String codeAcces) {
        LienExterne lien = lienExterneService.findByCodeAcces(codeAcces);
        if (lien == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lien);
    }

    @GetMapping("/magasin/{magasinId}")
    public List<LienExterne> getLiensByMagasin(@PathVariable Long magasinId) {
        return lienExterneService.findByMagasinId(magasinId);
    }

    @GetMapping("/utilise/{utilise}")
    public List<LienExterne> getLiensByUtilise(@PathVariable Boolean utilise) {
        return lienExterneService.findByUtilise(utilise);
    }

    @PostMapping
    public ResponseEntity<LienExterne> createLien(@RequestBody LienExterne lienExterne) {
        LienExterne saved = lienExterneService.create(lienExterne);
        if (saved == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LienExterne> updateLien(@PathVariable Long id, @RequestBody LienExterne lienDetails) {
        LienExterne result = lienExterneService.update(id, lienDetails);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/utiliser")
    public ResponseEntity<LienExterne> markAsUsed(@PathVariable Long id) {
        LienExterne result = lienExterneService.markAsUsed(id);
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLien(@PathVariable Long id) {
        if (lienExterneService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}