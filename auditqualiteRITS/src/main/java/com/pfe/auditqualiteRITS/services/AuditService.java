package com.pfe.auditqualiteRITS.services;

import com.pfe.auditqualiteRITS.DAO.AuditRepository;
import com.pfe.auditqualiteRITS.entite.Audit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditService {

    private final AuditRepository auditRepository;

    @Transactional(readOnly = true)
    public List<Audit> findAll() {
        return auditRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Audit findById(Long id) {
        return auditRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Audit> findByStatut(String statut) {
        return auditRepository.findByStatut(statut);
    }

    @Transactional(readOnly = true)
    public List<Audit> findByAuditeurId(Long auditeurId) {
        return auditRepository.findByAuditeurId(auditeurId);
    }

    @Transactional(readOnly = true)
    public List<Audit> findByMagasinId(Long magasinId) {
        return auditRepository.findByMagasinId(magasinId);
    }

    public Audit create(Audit audit) {
        // Validation
        if (audit.getMission() == null) {
            return null;
        }
        if (audit.getMagasin() == null) {
            return null;
        }
        if (audit.getStatut() == null || audit.getStatut().isEmpty()) {
            return null;
        }

        // Set default values
        if (audit.getDateRealisation() == null) {
            audit.setDateRealisation(LocalDateTime.now());
        }

        // Set default note if not provided
        if (audit.getNoteGlobale() == null) {
            audit.setNoteGlobale(0.0);
        }

        return auditRepository.save(audit);
    }

    public Audit update(Long id, Audit updated) {
        Audit existing = auditRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        if (updated.getDateRealisation() != null) {
            existing.setDateRealisation(updated.getDateRealisation());
        }
        if (updated.getNoteGlobale() != null) {
            existing.setNoteGlobale(updated.getNoteGlobale());
        }
        if (updated.getStatut() != null) {
            existing.setStatut(updated.getStatut());
        }
        if (updated.getCommentaire() != null) {
            existing.setCommentaire(updated.getCommentaire());
        }
        if (updated.getMission() != null) {
            existing.setMission(updated.getMission());
        }
        if (updated.getAuditeur() != null) {
            existing.setAuditeur(updated.getAuditeur());
        }
        if (updated.getMagasin() != null) {
            existing.setMagasin(updated.getMagasin());
        }
        if (updated.getCategories() != null) {
            existing.setCategories(updated.getCategories());
        }

        return auditRepository.save(existing);
    }

    public Audit updateStatut(Long id, String statut) {
        Audit audit = auditRepository.findById(id).orElse(null);
        if (audit == null) {
            return null;
        }

        audit.setStatut(statut);
        return auditRepository.save(audit);
    }

    public Audit updateNoteGlobale(Long id, Double noteGlobale) {
        Audit audit = auditRepository.findById(id).orElse(null);
        if (audit == null) {
            return null;
        }

        audit.setNoteGlobale(noteGlobale);
        return auditRepository.save(audit);
    }

    public boolean delete(Long id) {
        Audit audit = auditRepository.findById(id).orElse(null);
        if (audit == null) {
            return false;
        }
        auditRepository.delete(audit);
        return true;
    }
}