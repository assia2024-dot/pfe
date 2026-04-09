package com.pfe.auditqualiteRITS.controllers;

import com.pfe.auditqualiteRITS.entite.PieceJointe;
import com.pfe.auditqualiteRITS.services.PieceJointeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pieces-jointes")
@RequiredArgsConstructor
public class PieceJointeController {

    private final PieceJointeService pieceJointeService;

    @GetMapping
    public List<PieceJointe> getAll() {
        return pieceJointeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PieceJointe> getById(@PathVariable Long id) {
        PieceJointe pieceJointe = pieceJointeService.findById(id);
        if (pieceJointe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pieceJointe);
    }

    @GetMapping("/categorie/{categorieId}")
    public List<PieceJointe> getByCategorie(@PathVariable Long categorieId) {
        return pieceJointeService.findByCategorieId(categorieId);
    }

    @GetMapping("/type/{type}")
    public List<PieceJointe> getByType(@PathVariable String type) {
        return pieceJointeService.findByType(type);
    }

    @PostMapping
    public ResponseEntity<PieceJointe> create(@RequestBody PieceJointe pieceJointe) {
        PieceJointe saved = pieceJointeService.create(pieceJointe);
        if (saved == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PieceJointe> update(@PathVariable Long id, @RequestBody PieceJointe updated) {
        PieceJointe result = pieceJointeService.update(id, updated);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (pieceJointeService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}