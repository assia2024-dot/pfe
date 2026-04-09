package com.pfe.auditqualiteRITS.services;

import com.pfe.auditqualiteRITS.DAO.MagasinRepository;
import com.pfe.auditqualiteRITS.entite.Magasin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MagasinService {

    private final MagasinRepository magasinRepository;

    @Transactional(readOnly = true)
    public List<Magasin> findAll() {
        return magasinRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Magasin findById(Long id) {
        return magasinRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Magasin> findByVille(String ville) {
        return magasinRepository.findByVille(ville);
    }

    @Transactional(readOnly = true)
    public List<Magasin> findByRegionId(Long regionId) {
        return magasinRepository.findByRegionId(regionId);
    }

    @Transactional(readOnly = true)
    public List<Magasin> findByActif(Boolean actif) {
        return magasinRepository.findByActif(actif);
    }

    public Magasin create(Magasin magasin) {
        // Validation
        if (magasin.getNom() == null || magasin.getNom().isEmpty()) {
            return null;
        }
        if (magasin.getAdresse() == null || magasin.getAdresse().isEmpty()) {
            return null;
        }
        if (magasin.getVille() == null || magasin.getVille().isEmpty()) {
            return null;
        }
        if (magasin.getRegion() == null) {
            return null;
        }

        // Set default value for actif if not provided
        if (magasin.getActif() == null) {
            magasin.setActif(true);
        }

        return magasinRepository.save(magasin);
    }

    public Magasin update(Long id, Magasin updated) {
        Magasin existing = magasinRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        if (updated.getNom() != null) {
            existing.setNom(updated.getNom());
        }
        if (updated.getAdresse() != null) {
            existing.setAdresse(updated.getAdresse());
        }
        if (updated.getVille() != null) {
            existing.setVille(updated.getVille());
        }
        if (updated.getActif() != null) {
            existing.setActif(updated.getActif());
        }
        if (updated.getRegion() != null) {
            existing.setRegion(updated.getRegion());
        }

        return magasinRepository.save(existing);
    }

    public boolean delete(Long id) {
        Magasin magasin = magasinRepository.findById(id).orElse(null);
        if (magasin == null) {
            return false;
        }

        // Check if magasin has associated audits or missions
        if (magasin.getAudits() != null && !magasin.getAudits().isEmpty()) {
            return false; // Cannot delete magasin with existing audits
        }
        if (magasin.getMissions() != null && !magasin.getMissions().isEmpty()) {
            return false; // Cannot delete magasin with existing missions
        }

        magasinRepository.delete(magasin);
        return true;
    }
}