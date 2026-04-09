package com.pfe.auditqualiteRITS.controllers;

import com.pfe.auditqualiteRITS.entite.Region;
import com.pfe.auditqualiteRITS.services.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public List<Region> getAll() {
        return regionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> getById(@PathVariable Long id) {
        Region region = regionService.findById(id);
        if (region == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(region);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Region> getByCode(@PathVariable String code) {
        Region region = regionService.findByCode(code);
        if (region == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(region);
    }

    @PostMapping
    public ResponseEntity<Region> create(@RequestBody Region region) {
        Region saved = regionService.create(region);
        if (saved == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Region> update(@PathVariable Long id, @RequestBody Region updated) {
        Region result = regionService.update(id, updated);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (regionService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}