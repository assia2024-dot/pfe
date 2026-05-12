package com.PFE.electroplanetaudit.service;

import com.PFE.electroplanetaudit.dto.GradeRequest;
import com.PFE.electroplanetaudit.entity.*;
import com.PFE.electroplanetaudit.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditSessionService {

    private final AuditSessionRepository auditSessionRepository;
    private final ElementScoreRepository elementScoreRepository;
    private final AuditMissionRepository auditMissionRepository;
    private final AuditElementRepository auditElementRepository;

    // ===== START AUDIT FROM MISSION =====
    public AuditSession startFromMission(Long missionId, Long auditeurId) {

        // 1. Get the mission
        AuditMission mission = auditMissionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Mission not found"));

        // 2. Verify mission belongs to this auditor
        if (!mission.getAuditeur().getId().equals(auditeurId)) {
            throw new RuntimeException("This mission is not assigned to you");
        }

        // 3. Verify mission status is PLANIFIEE
        if (mission.getStatut() != MissionStatus.PLANIFIEE) {
            throw new RuntimeException("Mission is already started or completed");
        }

        // 4. Verify mission doesn't already have a session
        if (auditSessionRepository.existsByMissionId(missionId)) {
            throw new RuntimeException("Mission already has an audit session");
        }

        // 5. VALIDATION: Check if current date is before or equal to mission dateDebut
        LocalDate today = LocalDate.now();
        if (today.isBefore(mission.getDateDebut())) {
            throw new RuntimeException("Cannot start audit before scheduled date: " + mission.getDateDebut());
        }

        // 6. VALIDATION: Check if deadline is passed (if dateFin exists)
        if (mission.getDateFin() != null && today.isAfter(mission.getDateFin())) {
            throw new RuntimeException("Cannot start audit: Deadline was " + mission.getDateFin());
        }

        // 7. Create AuditSession
        AuditSession session = AuditSession.builder()
                .mission(mission)
                .store(mission.getStore())
                .auditeur(mission.getAuditeur())
                .dateDebut(LocalDateTime.now())
                .statut(SessionStatus.EN_COURS)
                .build();

        session = auditSessionRepository.save(session);

        // 8. Copy all AuditElements from Mission to Session (create empty ElementScores)
        for (AuditElement element : mission.getAuditElements()) {
            ElementScore elementScore = ElementScore.builder()
                    .auditSession(session)
                    .auditElement(element)
                    .score(null)  // Not graded yet
                    .build();
            elementScoreRepository.save(elementScore);
        }

        // 9. Update mission status
        mission.setStatut(MissionStatus.EN_COURS);
        auditMissionRepository.save(mission);

        return session;
    }

    // ===== GRADE ONE ELEMENT =====
    public ElementScore gradeElement(Long sessionId, Long elementId, Integer score, String commentaire) {

        // Validate score range (1-10)
        if (score < 1 || score > 10) {
            throw new RuntimeException("Score must be between 1 and 10");
        }

        AuditSession session = auditSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Audit session not found"));

        // Check if session is still in progress
        if (session.getStatut() != SessionStatus.EN_COURS) {
            throw new RuntimeException("Cannot grade: Audit session is already completed");
        }

        // Find or create ElementScore
        ElementScore elementScore = elementScoreRepository
                .findByAuditSessionIdAndAuditElementId(sessionId, elementId);

        if (elementScore == null) {
            AuditElement element = auditElementRepository.findById(elementId)
                    .orElseThrow(() -> new RuntimeException("Audit element not found"));

            elementScore = ElementScore.builder()
                    .auditSession(session)
                    .auditElement(element)
                    .build();
        }

        elementScore.setScore(score);
        elementScore.setCommentaire(commentaire);

        return elementScoreRepository.save(elementScore);
    }

    // ===== GRADE MULTIPLE ELEMENTS AT ONCE =====
    @Transactional
    public void gradeElements(Long sessionId, List<GradeRequest> grades) {
        for (GradeRequest grade : grades) {
            gradeElement(sessionId, grade.getElementId(), grade.getScore(), grade.getCommentaire());
        }
    }

    // ===== SUBMIT AUDIT =====
    public AuditSession submitAudit(Long sessionId) {

        AuditSession session = auditSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Audit session not found"));

        // Check if session is still in progress
        if (session.getStatut() != SessionStatus.EN_COURS) {
            throw new RuntimeException("Audit session is already completed");
        }

        // Get all scores for this session
        List<ElementScore> scores = elementScoreRepository.findByAuditSessionId(sessionId);

        if (scores.isEmpty()) {
            throw new RuntimeException("No elements to grade");
        }

        // Calculate global score
        double totalScore = 0;
        int gradedCount = 0;

        for (ElementScore elementScore : scores) {
            if (elementScore.getScore() != null) {
                totalScore += elementScore.getScore();
                gradedCount++;
            }
        }

        if (gradedCount == 0) {
            throw new RuntimeException("No elements have been graded yet");
        }

        double averageScore = totalScore / gradedCount;
        double globalScore = (averageScore / 10) * 100;  // Convert to percentage

        // VALIDATION: Check if deadline is passed
        AuditMission mission = session.getMission();
        if (mission.getDateFin() != null && LocalDate.now().isAfter(mission.getDateFin())) {
            // Still allow submission but maybe add warning
            // You can log this or add a flag
            System.out.println("Warning: Audit submitted after deadline: " + mission.getDateFin());
        }

        // Update session
        session.setNoteGlobale(globalScore);
        session.setDateFin(LocalDateTime.now());
        session.setStatut(SessionStatus.TERMINE);

        session = auditSessionRepository.save(session);

        // Update mission status
        mission.setStatut(MissionStatus.TERMINEE);
        auditMissionRepository.save(mission);

        return session;
    }

    // ===== GET SESSION WITH SCORES =====
    @Transactional(readOnly = true)
    public AuditSession findById(Long id) {
        return auditSessionRepository.findById(id).orElse(null);
    }

    // ===== GET ALL SESSIONS =====
    @Transactional(readOnly = true)
    public List<AuditSession> findAll() {
        return auditSessionRepository.findAll();
    }

    // ===== GET SESSIONS BY STORE =====
    @Transactional(readOnly = true)
    public List<AuditSession> findByStore(Long storeId) {
        return auditSessionRepository.findByStoreId(storeId);
    }

    // ===== GET SESSIONS BY AUDITOR =====
    @Transactional(readOnly = true)
    public List<AuditSession> findByAuditeur(Long auditeurId) {
        return auditSessionRepository.findByAuditeur(
                User.builder().id(auditeurId).build()
        );
    }

    // ===== GET SESSIONS WITH FILTERS =====
    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<AuditSession> getSessionsWithFilters(
            Long storeId, Long auditeurId, SessionStatus statut, org.springframework.data.domain.Pageable pageable) {
        return auditSessionRepository.findAllWithFilters(storeId, auditeurId, statut, pageable);
    }

    // ===== GET MY SESSIONS (AUDITEUR) =====
    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<AuditSession> getMySessions(
            Long auditeurId, SessionStatus statut, org.springframework.data.domain.Pageable pageable) {
        return auditSessionRepository.findAllWithFilters(null, auditeurId, statut, pageable);
    }

    // ===== GET SCORES BY SESSION =====
    @Transactional(readOnly = true)
    public List<ElementScore> getScoresBySession(Long sessionId) {
        return elementScoreRepository.findByAuditSessionId(sessionId);
    }

    // ===== CHECK IF MISSION CAN BE STARTED =====
    @Transactional(readOnly = true)
    public boolean canStartMission(Long missionId, Long auditeurId) {
        AuditMission mission = auditMissionRepository.findById(missionId).orElse(null);
        if (mission == null) return false;

        // Check mission belongs to auditor
        if (!mission.getAuditeur().getId().equals(auditeurId)) return false;

        // Check mission status
        if (mission.getStatut() != MissionStatus.PLANIFIEE) return false;

        // Check if session already exists
        if (auditSessionRepository.existsByMissionId(missionId)) return false;

        // Check date validation
        LocalDate today = LocalDate.now();
        if (today.isBefore(mission.getDateDebut())) return false;

        if (mission.getDateFin() != null && today.isAfter(mission.getDateFin())) return false;

        return true;
    }

    // ===== DELETE SESSION =====
    public boolean delete(Long id) {
        if (!auditSessionRepository.existsById(id)) {
            return false;
        }
        auditSessionRepository.deleteById(id);
        return true;
    }

    // ===== STATISTICS FOR BI =====
    @Transactional(readOnly = true)
    public List<Object[]> getAverageScoreByStore() {
        return auditSessionRepository.getAverageScoreByStore();
    }

    @Transactional(readOnly = true)
    public List<Object[]> getAverageScoreByAuditeur() {
        return auditSessionRepository.getAverageScoreByAuditeur();
    }

    @Transactional(readOnly = true)
    public List<Object[]> getAverageScoreByRegion() {
        return auditSessionRepository.getAverageScoreByRegion();
    }

    @Transactional(readOnly = true)
    public List<Object[]> getMonthlyAuditCount() {
        return auditSessionRepository.getMonthlyAuditCount();
    }

    @Transactional(readOnly = true)
    public List<Object[]> getElementPerformanceSummary() {
        return elementScoreRepository.getElementPerformanceSummary();
    }
}