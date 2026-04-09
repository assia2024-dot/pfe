package com.pfe.auditqualiteRITS.services;

import com.pfe.auditqualiteRITS.DAO.MissionRepository;
import com.pfe.auditqualiteRITS.entite.Mission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionService {

    private final MissionRepository missionRepository;

    @Transactional(readOnly = true)
    public List<Mission> findAll() {
        return missionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Mission findById(Long id) {
        return missionRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Mission> findByStatut(String statut) {
        return missionRepository.findByStatut(statut);
    }

    @Transactional(readOnly = true)
    public List<Mission> findByAuditeurId(Long auditeurId) {
        return missionRepository.findByAuditeurId(auditeurId);
    }

    @Transactional(readOnly = true)
    public List<Mission> findByMagasinId(Long magasinId) {
        return missionRepository.findByMagasinId(magasinId);
    }

    public Mission create(Mission mission) {
        // Validation
        if (mission.getTitre() == null || mission.getTitre().isEmpty()) {
            return null;
        }
        if (mission.getDateDebut() == null) {
            return null;
        }
        if (mission.getDateFin() == null) {
            return null;
        }
        if (mission.getMagasin() == null) {
            return null;
        }

        // Validate dates
        if (mission.getDateFin().isBefore(mission.getDateDebut())) {
            return null;
        }

        // Set default statut if not provided
        if (mission.getStatut() == null || mission.getStatut().isEmpty()) {
            mission.setStatut("PLANIFIEE");
        }

        // Set default type if not provided
        if (mission.getType() == null || mission.getType().isEmpty()) {
            mission.setType("INTERNE");
        }

        return missionRepository.save(mission);
    }

    public Mission update(Long id, Mission updated) {
        Mission existing = missionRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        if (updated.getTitre() != null) {
            existing.setTitre(updated.getTitre());
        }
        if (updated.getDateDebut() != null) {
            existing.setDateDebut(updated.getDateDebut());
        }
        if (updated.getDateFin() != null) {
            existing.setDateFin(updated.getDateFin());
        }
        if (updated.getStatut() != null) {
            existing.setStatut(updated.getStatut());
        }
        if (updated.getType() != null) {
            existing.setType(updated.getType());
        }
        if (updated.getAuditeur() != null) {
            existing.setAuditeur(updated.getAuditeur());
        }
        if (updated.getAdministrateur() != null) {
            existing.setAdministrateur(updated.getAdministrateur());
        }
        if (updated.getMagasin() != null) {
            existing.setMagasin(updated.getMagasin());
        }

        // Validate dates after update
        if (existing.getDateDebut() != null && existing.getDateFin() != null) {
            if (existing.getDateFin().isBefore(existing.getDateDebut())) {
                return null;
            }
        }

        return missionRepository.save(existing);
    }

    public boolean delete(Long id) {
        Mission mission = missionRepository.findById(id).orElse(null);
        if (mission == null) {
            return false;
        }
        missionRepository.delete(mission);
        return true;
    }
}