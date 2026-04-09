package com.pfe.auditqualiteRITS.controllers;

import com.pfe.auditqualiteRITS.entite.Mission;
import com.pfe.auditqualiteRITS.services.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping
    public List<Mission> getAll() {
        return missionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mission> getById(@PathVariable Long id) {
        Mission mission = missionService.findById(id);
        if (mission == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mission);
    }

    @GetMapping("/auditeur/{auditeurId}")
    public List<Mission> getByAuditeur(@PathVariable Long auditeurId) {
        return missionService.findByAuditeurId(auditeurId);
    }

    @GetMapping("/magasin/{magasinId}")
    public List<Mission> getByMagasin(@PathVariable Long magasinId) {
        return missionService.findByMagasinId(magasinId);
    }

    @GetMapping("/statut/{statut}")
    public List<Mission> getByStatut(@PathVariable String statut) {
        return missionService.findByStatut(statut);
    }

    @PostMapping
    public ResponseEntity<Mission> create(@RequestBody Mission mission) {
        Mission saved = missionService.create(mission);
        if (saved == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mission> update(@PathVariable Long id, @RequestBody Mission updated) {
        Mission result = missionService.update(id, updated);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (missionService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}