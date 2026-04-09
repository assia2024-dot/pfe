package com.pfe.auditqualiteRITS.services;

import com.pfe.auditqualiteRITS.DAO.LienExterneRepository;
import com.pfe.auditqualiteRITS.entite.LienExterne;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LienExterneService {

    private final LienExterneRepository lienExterneRepository;

    @Transactional(readOnly = true)
    public List<LienExterne> findAll() {
        return lienExterneRepository.findAll();
    }

    @Transactional(readOnly = true)
    public LienExterne findById(Long id) {
        return lienExterneRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public LienExterne findByCodeAcces(String codeAcces) {
        return lienExterneRepository.findByCodeAcces(codeAcces);
    }

    @Transactional(readOnly = true)
    public List<LienExterne> findByMagasinId(Long magasinId) {
        return lienExterneRepository.findByMagasinId(magasinId);
    }

    @Transactional(readOnly = true)
    public List<LienExterne> findByUtilise(Boolean utilise) {
        return lienExterneRepository.findByUtilise(utilise);
    }

    public LienExterne create(LienExterne lienExterne) {
        // Validation
        if (lienExterne.getUrl() == null || lienExterne.getUrl().isEmpty()) {
            return null;
        }
        if (lienExterne.getCodeAcces() == null || lienExterne.getCodeAcces().isEmpty()) {
            return null;
        }
        if (lienExterne.getAdministrateur() == null) {
            return null;
        }
        if (lienExterne.getMagasin() == null) {
            return null;
        }

        // Set expiration date to 7 days from now if not provided
        if (lienExterne.getExpiresAt() == null) {
            lienExterne.setExpiresAt(LocalDateTime.now().plusDays(7));
        }

        // Set initial utilization status to false
        lienExterne.setUtilise(Boolean.FALSE);

        return lienExterneRepository.save(lienExterne);
    }

    public LienExterne update(Long id, LienExterne updated) {
        LienExterne existing = lienExterneRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        if (updated.getUrl() != null) {
            existing.setUrl(updated.getUrl());
        }
        if (updated.getCodeAcces() != null) {
            existing.setCodeAcces(updated.getCodeAcces());
        }
        if (updated.getExpiresAt() != null) {
            existing.setExpiresAt(updated.getExpiresAt());
        }
        if (updated.getUtilise() != null) {
            existing.setUtilise(updated.getUtilise());
        }
        if (updated.getTelephone() != null) {
            existing.setTelephone(updated.getTelephone());
        }
        if (updated.getAdministrateur() != null) {
            existing.setAdministrateur(updated.getAdministrateur());
        }
        if (updated.getMagasin() != null) {
            existing.setMagasin(updated.getMagasin());
        }
        if (updated.getAuditeurExterne() != null) {
            existing.setAuditeurExterne(updated.getAuditeurExterne());
        }

        return lienExterneRepository.save(existing);
    }

    public LienExterne markAsUsed(Long id) {
        LienExterne lien = lienExterneRepository.findById(id).orElse(null);
        if (lien == null) {
            return null;
        }

        // Check if link is expired
        if (lien.getExpiresAt() != null && lien.getExpiresAt().isBefore(LocalDateTime.now())) {
            return null; // Link expired
        }

        // Check if already used
        if (lien.getUtilise()) {
            return null; // Link already used
        }

        lien.setUtilise(Boolean.TRUE);
        return lienExterneRepository.save(lien);
    }

    public boolean delete(Long id) {
        LienExterne lien = lienExterneRepository.findById(id).orElse(null);
        if (lien == null) {
            return false;
        }
        lienExterneRepository.delete(lien);
        return true;
    }
}