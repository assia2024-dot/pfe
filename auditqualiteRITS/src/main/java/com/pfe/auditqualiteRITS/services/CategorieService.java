package com.pfe.auditqualiteRITS.services;

import com.pfe.auditqualiteRITS.DAO.CategorieRepository;
import com.pfe.auditqualiteRITS.entite.Categorie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategorieService {

    private final CategorieRepository categorieRepository;

    @Transactional(readOnly = true)
    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Categorie findById(Long id) {
        return categorieRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Categorie> findByAuditId(Long auditId) {
        return categorieRepository.findByAuditsId(auditId);
    }

    public Categorie create(Categorie categorie) {
        // Validation
        if (categorie.getNom() == null || categorie.getNom().isEmpty()) {
            return null;
        }

        // Set default note if not provided
        if (categorie.getNote() == null) {
            categorie.setNote(0);
        }

        return categorieRepository.save(categorie);
    }

    public Categorie update(Long id, Categorie updated) {
        Categorie existing = categorieRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        if (updated.getNom() != null) {
            existing.setNom(updated.getNom());
        }
        if (updated.getDescription() != null) {
            existing.setDescription(updated.getDescription());
        }
        if (updated.getNote() != null) {
            existing.setNote(updated.getNote());
        }

        return categorieRepository.save(existing);
    }

    public boolean delete(Long id) {
        Categorie categorie = categorieRepository.findById(id).orElse(null);
        if (categorie == null) {
            return false;
        }

        // Check if category has associated audits or pieces jointes
        if (categorie.getAudits() != null && !categorie.getAudits().isEmpty()) {
            return false; // Cannot delete category with existing audits
        }
        if (categorie.getPiecesJointes() != null && !categorie.getPiecesJointes().isEmpty()) {
            return false; // Cannot delete category with existing pieces jointes
        }

        categorieRepository.delete(categorie);
        return true;
    }
}