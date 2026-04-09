package com.pfe.auditqualiteRITS.services;

import com.pfe.auditqualiteRITS.DAO.AuditeurRepository;
import com.pfe.auditqualiteRITS.entite.Auditeur;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditeurService {

    private final AuditeurRepository auditeurRepository;

    @Transactional(readOnly = true)
    public List<Auditeur> findAll() {
        return auditeurRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Auditeur findById(Long id) {
        return auditeurRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Auditeur> findByRegion(String region) {
        return auditeurRepository.findByRegion(region);
    }

    public Auditeur create(Auditeur auditeur) {
        // Validation
        if (auditeur.getNom() == null || auditeur.getNom().isEmpty()) {
            return null;
        }
        if (auditeur.getPrenom() == null || auditeur.getPrenom().isEmpty()) {
            return null;
        }
        if (auditeur.getEmail() == null || auditeur.getEmail().isEmpty()) {
            return null;
        }
        if (auditeur.getMotDePasse() == null || auditeur.getMotDePasse().isEmpty()) {
            return null;
        }

        // Check if email already exists
        if (auditeurRepository.existsByEmail(auditeur.getEmail())) {
            return null;
        }

        // Set default values from Utilisateur
        if (auditeur.getRole() == null || auditeur.getRole().isEmpty()) {
            auditeur.setRole("AUDITEUR");
        }
        if (auditeur.getActif() == null) {
            auditeur.setActif(true);
        }

        return auditeurRepository.save(auditeur);
    }

    public Auditeur update(Long id, Auditeur updated) {
        Auditeur existing = auditeurRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        // Update Utilisateur fields
        if (updated.getNom() != null) {
            existing.setNom(updated.getNom());
        }
        if (updated.getPrenom() != null) {
            existing.setPrenom(updated.getPrenom());
        }
        if (updated.getEmail() != null) {
            existing.setEmail(updated.getEmail());
        }
        if (updated.getMotDePasse() != null) {
            existing.setMotDePasse(updated.getMotDePasse());
        }
        if (updated.getRole() != null) {
            existing.setRole(updated.getRole());
        }
        if (updated.getActif() != null) {
            existing.setActif(updated.getActif());
        }

        // Update Auditeur specific fields
        if (updated.getTelephone() != null) {
            existing.setTelephone(updated.getTelephone());
        }
        if (updated.getRegion() != null) {
            existing.setRegion(updated.getRegion());
        }

        return auditeurRepository.save(existing);
    }

    public boolean delete(Long id) {
        Auditeur auditeur = auditeurRepository.findById(id).orElse(null);
        if (auditeur == null) {
            return false;
        }

        // Check if auditor has associated missions or audits
        if (auditeur.getMissions() != null && !auditeur.getMissions().isEmpty()) {
            return false;
        }
        if (auditeur.getAudits() != null && !auditeur.getAudits().isEmpty()) {
            return false;
        }

        auditeurRepository.delete(auditeur);
        return true;
    }
}