package com.pfe.auditqualiteRITS.services;

import com.pfe.auditqualiteRITS.DAO.PieceJointeRepository;
import com.pfe.auditqualiteRITS.entite.PieceJointe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PieceJointeService {

    private final PieceJointeRepository pieceJointeRepository;

    @Transactional(readOnly = true)
    public List<PieceJointe> findAll() {
        return pieceJointeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PieceJointe findById(Long id) {
        return pieceJointeRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<PieceJointe> findByCategorieId(Long categorieId) {
        return pieceJointeRepository.findByCategorieId(categorieId);
    }

    @Transactional(readOnly = true)
    public List<PieceJointe> findByType(String type) {
        return pieceJointeRepository.findByType(type);
    }

    public PieceJointe create(PieceJointe pieceJointe) {
        // Validation
        if (pieceJointe.getUrl() == null || pieceJointe.getUrl().isEmpty()) {
            return null;
        }
        if (pieceJointe.getType() == null || pieceJointe.getType().isEmpty()) {
            return null;
        }
        if (pieceJointe.getCategorie() == null) {
            return null;
        }

        // Set upload date if not provided
        if (pieceJointe.getUploadedAt() == null) {
            pieceJointe.setUploadedAt(LocalDateTime.now());
        }

        // Set default taille if not provided
        if (pieceJointe.getTaille() == null) {
            pieceJointe.setTaille(Long.valueOf(0));
        }

        return pieceJointeRepository.save(pieceJointe);
    }

    public PieceJointe update(Long id, PieceJointe updated) {
        PieceJointe existing = pieceJointeRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        if (updated.getUrl() != null) {
            existing.setUrl(updated.getUrl());
        }
        if (updated.getType() != null) {
            existing.setType(updated.getType());
        }
        if (updated.getTaille() != null) {
            existing.setTaille(updated.getTaille());
        }
        if (updated.getCategorie() != null) {
            existing.setCategorie(updated.getCategorie());
        }

        return pieceJointeRepository.save(existing);
    }

    public boolean delete(Long id) {
        PieceJointe pieceJointe = pieceJointeRepository.findById(id).orElse(null);
        if (pieceJointe == null) {
            return false;
        }
        pieceJointeRepository.delete(pieceJointe);
        return true;
    }
}