package com.pfe.auditqualiteRITS.services;

import com.pfe.auditqualiteRITS.DAO.AdministrateurRepository;
import com.pfe.auditqualiteRITS.entite.Administrateur;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdministrateurService {

    private final AdministrateurRepository administrateurRepository;

    @Transactional(readOnly = true)
    public List<Administrateur> findAll() {
        return administrateurRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Administrateur findById(Long id) {
        return administrateurRepository.findById(id).orElse(null);
    }

    public Administrateur create(Administrateur administrateur) {
        // Validation
        if (administrateur.getNom() == null || administrateur.getNom().isEmpty()) {
            return null;
        }
        if (administrateur.getPrenom() == null || administrateur.getPrenom().isEmpty()) {
            return null;
        }
        if (administrateur.getEmail() == null || administrateur.getEmail().isEmpty()) {
            return null;
        }
        if (administrateur.getMotDePasse() == null || administrateur.getMotDePasse().isEmpty()) {
            return null;
        }

        //Check if email already exists
        if (administrateurRepository.existsByEmail(administrateur.getEmail())) {
            return null;
        }

        //Set default values from Utilisateur
        if (administrateur.getRole() == null || administrateur.getRole().isEmpty()) {
            administrateur.setRole("Auditeur");
        }
        if (administrateur.getActif() == null) {
            administrateur.setActif(true);
        }

        return administrateurRepository.save(administrateur);
    }

    public Administrateur update(Long id, Administrateur updated) {
        Administrateur existing = administrateurRepository.findById(id).orElse(null);
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

        return administrateurRepository.save(existing);
    }

    public Administrateur updateActif(Long id, Boolean actif) {
        Administrateur administrateur = administrateurRepository.findById(id).orElse(null);
        if (administrateur == null) {
            return null;
        }

        administrateur.setActif(actif);
        return administrateurRepository.save(administrateur);
    }

    public boolean delete(Long id) {
        Administrateur administrateur = administrateurRepository.findById(id).orElse(null);
        if (administrateur == null) {
            return false;
        }

        // Check if admin has associated missions
        if (administrateur.getMissions() != null && !administrateur.getMissions().isEmpty()) {
            return false;
        }

        // Check if admin has associated external links
        if (administrateur.getLiensExternes() != null && !administrateur.getLiensExternes().isEmpty()) {
            return false;
        }

        administrateurRepository.delete(administrateur);
        return true;
    }
}