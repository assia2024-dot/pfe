package com.PFE.electroplanetaudit.service;

import com.PFE.electroplanetaudit.entity.*;
import com.PFE.electroplanetaudit.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditMissionService {

    private final AuditMissionRepository auditMissionRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final AuditElementRepository auditElementRepository;

    // ===== CREATE =====
    public AuditMission create(AuditMission mission, List<Long> auditElementIds, Long adminId) {
        // Validate store
        Store store = storeRepository.findById(mission.getStore().getId())
                .orElseThrow(() -> new RuntimeException("Store not found"));
        mission.setStore(store);

        // Validate auditor
        User auditeur = userRepository.findById(mission.getAuditeur().getId())
                .orElseThrow(() -> new RuntimeException("Auditor not found"));
        mission.setAuditeur(auditeur);

        // Set admin who created this mission
        if (adminId != null) {
            User admin = userRepository.findById(adminId).orElse(null);
            mission.setAdministrateur(admin);
        }

        // Set default status
        if (mission.getStatut() == null) {
            mission.setStatut(MissionStatus.PLANIFIEE);
        }

        // Add selected audit elements
        if (auditElementIds != null && !auditElementIds.isEmpty()) {
            List<AuditElement> elements = auditElementRepository.findAllById(auditElementIds);
            mission.setAuditElements(elements);
        }

        mission.setDateCreation(LocalDateTime.now());

        return auditMissionRepository.save(mission);
    }

    // ===== READ - By auditor userCode =====
    @Transactional(readOnly = true)
    public List<AuditMission> findByAuditeurCode(Integer userCode) {
        User auditeur = userRepository.findByUserCode(userCode).orElse(null);
        if (auditeur == null) return List.of();
        return auditMissionRepository.findByAuditeur(auditeur);
    }

    // ===== READ - For logged-in auditor using userCode =====
    @Transactional(readOnly = true)
    public Page<AuditMission> getMissionsByAuditeurCode(Integer userCode, MissionStatus status, Pageable pageable) {
        User auditeur = userRepository.findByUserCode(userCode).orElse(null);
        if (auditeur == null) return Page.empty();
        return auditMissionRepository.findMissionsByAuditeurId(auditeur.getId(), status, pageable);
    }

    // ===== READ - All (admin only) =====
    @Transactional(readOnly = true)
    public List<AuditMission> findAll() {
        return auditMissionRepository.findAll();
    }

    // ===== READ - By ID =====
    @Transactional(readOnly = true)
    public AuditMission findById(Long id) {
        return auditMissionRepository.findById(id).orElse(null);
    }

    // ===== READ - By auditor =====
    @Transactional(readOnly = true)
    public List<AuditMission> findByAuditeur(Long auditeurId) {
        User auditeur = userRepository.findById(auditeurId).orElse(null);
        if (auditeur == null) return List.of();
        return auditMissionRepository.findByAuditeur(auditeur);
    }

    // ===== READ - By store =====
    @Transactional(readOnly = true)
    public List<AuditMission> findByStore(Long storeId) {
        return auditMissionRepository.findByStoreId(storeId);
    }

    // ===== READ - By status =====
    @Transactional(readOnly = true)
    public List<AuditMission> findByStatus(MissionStatus status) {
        return auditMissionRepository.findByStatut(status);
    }

    // ===== READ - Search =====
    @Transactional(readOnly = true)
    public List<AuditMission> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return auditMissionRepository.findAll();
        }
        return auditMissionRepository.searchByKeyword(keyword);
    }

    // ===== READ - With filters and pagination (admin) =====
    @Transactional(readOnly = true)
    public Page<AuditMission> getMissionsWithFilters(String keyword, MissionStatus status,
                                                     Long storeId, Long auditeurId, Pageable pageable) {
        return auditMissionRepository.findAllWithFilters(keyword, status, storeId, auditeurId, pageable);
    }

    // ===== READ - For logged-in auditor (only their missions) =====
    @Transactional(readOnly = true)
    public Page<AuditMission> getMyMissions(Long auditeurId, MissionStatus status, Pageable pageable) {
        return auditMissionRepository.findMissionsByAuditeurId(auditeurId, status, pageable);
    }

    // ===== READ - Statistics for BI =====
    @Transactional(readOnly = true)
    public List<Object[]> getStatsByStatus() {
        return auditMissionRepository.countByStatus();
    }

    @Transactional(readOnly = true)
    public List<Object[]> getStatsByStore() {
        return auditMissionRepository.countByStore();
    }

    @Transactional(readOnly = true)
    public List<Object[]> getStatsByAuditeur() {
        return auditMissionRepository.countByAuditeur();
    }

    // ===== READ - Upcoming missions =====
    @Transactional(readOnly = true)
    public List<AuditMission> getUpcomingMissions() {
        return auditMissionRepository.findUpcomingMissions();
    }

    // ===== UPDATE - Full update =====
    public AuditMission update(Long id, AuditMission updatedMission, List<Long> auditElementIds) {
        AuditMission existing = auditMissionRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        // Update basic fields
        if (updatedMission.getTitle() != null) existing.setTitle(updatedMission.getTitle());
        if (updatedMission.getDescription() != null) existing.setDescription(updatedMission.getDescription());
        if (updatedMission.getDateDebut() != null) existing.setDateDebut(updatedMission.getDateDebut());
        if (updatedMission.getDateFin() != null) existing.setDateFin(updatedMission.getDateFin());
        if (updatedMission.getStatut() != null) existing.setStatut(updatedMission.getStatut());

        // Update store if changed
        if (updatedMission.getStore() != null && updatedMission.getStore().getId() != null) {
            Store store = storeRepository.findById(updatedMission.getStore().getId()).orElse(null);
            if (store != null) existing.setStore(store);
        }

        // Update auditor if changed
        if (updatedMission.getAuditeur() != null && updatedMission.getAuditeur().getId() != null) {
            User auditeur = userRepository.findById(updatedMission.getAuditeur().getId()).orElse(null);
            if (auditeur != null) existing.setAuditeur(auditeur);
        }

        // Update audit elements if provided
        if (auditElementIds != null) {
            List<AuditElement> elements = auditElementRepository.findAllById(auditElementIds);
            existing.setAuditElements(elements);
        }

        return auditMissionRepository.save(existing);
    }

    // ===== UPDATE - Change status only =====
    public AuditMission updateStatus(Long id, MissionStatus status) {
        AuditMission existing = auditMissionRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setStatut(status);
        return auditMissionRepository.save(existing);
    }

    // ===== DELETE =====
    public boolean delete(Long id) {
        if (!auditMissionRepository.existsById(id)) {
            return false;
        }
        auditMissionRepository.deleteById(id);
        return true;
    }
}