package com.pfe.auditqualiteRITS.services;

import com.pfe.auditqualiteRITS.DAO.AuditeurExterneRepository;
import com.pfe.auditqualiteRITS.entite.AuditeurExterne;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditeurExterneService {

    private final AuditeurExterneRepository auditeurExterneRepository;

    @Transactional(readOnly = true)
    public List<AuditeurExterne> findAll() {
        return auditeurExterneRepository.findAll();
    }

    @Transactional(readOnly = true)
    public AuditeurExterne findById(Long id) {
        return auditeurExterneRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public AuditeurExterne findByNom(String nom) {
        return auditeurExterneRepository.findByNom(nom);
    }

    @Transactional(readOnly = true)
    public List<AuditeurExterne> findByStatut(String statut) {
        return auditeurExterneRepository.findByStatut(statut);
    }

    public AuditeurExterne create(AuditeurExterne auditeurExterne) {
        // Validation
        if (auditeurExterne.getNom() == null || auditeurExterne.getNom().isEmpty()) {
            return null;
        }
        if (auditeurExterne.getStatut() == null || auditeurExterne.getStatut().isEmpty()) {
            return null;
        }

        // Set default statut if not provided
        if (auditeurExterne.getStatut() == null) {
            auditeurExterne.setStatut("ACTIF");
        }

        // Set date acces to current time if not provided
        if (auditeurExterne.getDateAcces() == null) {
            auditeurExterne.setDateAcces(LocalDateTime.now());
        }

        return auditeurExterneRepository.save(auditeurExterne);
    }

    public AuditeurExterne update(Long id, AuditeurExterne updated) {
        AuditeurExterne existing = auditeurExterneRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        if (updated.getNom() != null) {
            existing.setNom(updated.getNom());
        }
        if (updated.getTelephone() != null) {
            existing.setTelephone(updated.getTelephone());
        }
        if (updated.getStatut() != null) {
            existing.setStatut(updated.getStatut());
        }
        if (updated.getDateAcces() != null) {
            existing.setDateAcces(updated.getDateAcces());
        }

        return auditeurExterneRepository.save(existing);
    }

    public AuditeurExterne updateStatut(Long id, String statut) {
        AuditeurExterne auditeur = auditeurExterneRepository.findById(id).orElse(null);
        if (auditeur == null) {
            return null;
        }

        auditeur.setStatut(statut);
        return auditeurExterneRepository.save(auditeur);
    }

    public boolean delete(Long id) {
        AuditeurExterne auditeurExterne = auditeurExterneRepository.findById(id).orElse(null);
        if (auditeurExterne == null) {
            return false;
        }

        // Check if external auditor has an associated mission
        if (auditeurExterne.getMission() != null) {
            return false; // Cannot delete external auditor with existing mission
        }

        auditeurExterneRepository.delete(auditeurExterne);
        return true;
    }
}