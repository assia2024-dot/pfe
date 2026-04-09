package com.pfe.auditqualiteRITS.services;

import com.pfe.auditqualiteRITS.DAO.UtilisateurRepository;
import com.pfe.auditqualiteRITS.entite.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    @Transactional(readOnly = true)
    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Utilisateur findById(Long id) {
        return utilisateurRepository.findById(id).orElse(null);
    }

    public Utilisateur create(Utilisateur utilisateur) {
        if (utilisateur.getEmail() == null || utilisateur.getEmail().isEmpty()) {
            return null;
        }
        if (utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
            return null;
        }
        if (utilisateur.getRole() == null || utilisateur.getRole().isEmpty()) {
            utilisateur.setRole("USER");
        }
        if (utilisateur.getActif() == null) {
            utilisateur.setActif(Boolean.TRUE);
        }
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur update(Long id, Utilisateur updated) {
        Utilisateur existing = utilisateurRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        if (updated.getNom() != null) existing.setNom(updated.getNom());
        if (updated.getPrenom() != null) existing.setPrenom(updated.getPrenom());
        if (updated.getEmail() != null) existing.setEmail(updated.getEmail());
        if (updated.getRole() != null) existing.setRole(updated.getRole());
        if (updated.getActif() != null) existing.setActif(updated.getActif());

        return utilisateurRepository.save(existing);
    }

    public boolean delete(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElse(null);
        if (utilisateur == null) {
            return false;
        }
        utilisateurRepository.delete(utilisateur);
        return true;
    }

    @Transactional(readOnly = true)
    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }
}