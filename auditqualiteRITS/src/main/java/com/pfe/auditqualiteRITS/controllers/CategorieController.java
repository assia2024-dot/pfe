package com.pfe.auditqualiteRITS.controllers;

import com.pfe.auditqualiteRITS.entite.Categorie;
import com.pfe.auditqualiteRITS.services.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategorieController {

    private final CategorieService categorieService;

    @GetMapping
    public List<Categorie> getAll() {
        return categorieService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getById(@PathVariable Long id) {
        Categorie categorie = categorieService.findById(id);
        if (categorie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categorie);
    }

    @GetMapping("/audit/{auditId}")
    public List<Categorie> getByAudit(@PathVariable Long auditId) {
        return categorieService.findByAuditId(auditId);
    }

    @PostMapping
    public ResponseEntity<Categorie> create(@RequestBody Categorie categorie) {
        Categorie saved = categorieService.create(categorie);
        if (saved == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorie> update(@PathVariable Long id, @RequestBody Categorie updated) {
        Categorie result = categorieService.update(id, updated);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (categorieService.delete(id)) {
            return ResponseEntity.ok("Category deleted successfully");
        }
        return ResponseEntity.badRequest().body("Cannot delete category with existing audits or pieces jointes");
    }
}