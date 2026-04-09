package com.pfe.auditqualiteRITS.controllers;

import com.pfe.auditqualiteRITS.entite.Magasin;
import com.pfe.auditqualiteRITS.services.MagasinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/magasins")
@RequiredArgsConstructor
public class MagasinController {

    private final MagasinService magasinService;

    @GetMapping
    public List<Magasin> getAll() {
        return magasinService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Magasin> getById(@PathVariable Long id) {
        Magasin magasin = magasinService.findById(id);
        if (magasin == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(magasin);
    }

    @GetMapping("/ville/{ville}")
    public List<Magasin> getByVille(@PathVariable String ville) {
        return magasinService.findByVille(ville);
    }

    @GetMapping("/region/{regionId}")
    public List<Magasin> getByRegion(@PathVariable Long regionId) {
        return magasinService.findByRegionId(regionId);
    }

    @GetMapping("/actif/{actif}")
    public List<Magasin> getByActif(@PathVariable Boolean actif) {
        return magasinService.findByActif(actif);
    }

    @PostMapping
    public ResponseEntity<Magasin> create(@RequestBody Magasin magasin) {
        Magasin saved = magasinService.create(magasin);
        if (saved == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Magasin> update(@PathVariable Long id, @RequestBody Magasin updated) {
        Magasin result = magasinService.update(id, updated);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (magasinService.delete(id)) {
            return ResponseEntity.ok("Magasin deleted successfully");
        }
        return ResponseEntity.badRequest().body("Cannot delete magasin with existing audits or missions");
    }
}