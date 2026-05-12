package com.PFE.electroplanetaudit.repository;

import com.PFE.electroplanetaudit.entity.AuditSession;
import com.PFE.electroplanetaudit.entity.SessionStatus;
import com.PFE.electroplanetaudit.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditSessionRepository extends JpaRepository<AuditSession, Long> {

    // Find by store
    List<AuditSession> findByStoreId(Long storeId);

    // Find by auditor
    List<AuditSession> findByAuditeur(User auditeur);

    // Find by mission
    List<AuditSession> findByMissionId(Long missionId);

    // Find by status
    List<AuditSession> findByStatut(SessionStatus statut);

    // Find by date range
    List<AuditSession> findByDateDebutBetween(LocalDateTime start, LocalDateTime end);

    // Check if mission already has a session
    boolean existsByMissionId(Long missionId);

    // Search with pagination and filters
    @Query("SELECT s FROM AuditSession s WHERE " +
            "(:storeId IS NULL OR s.store.id = :storeId) " +
            "AND (:auditeurId IS NULL OR s.auditeur.id = :auditeurId) " +
            "AND (:statut IS NULL OR s.statut = :statut)")
    Page<AuditSession> findAllWithFilters(@Param("storeId") Long storeId,
                                          @Param("auditeurId") Long auditeurId,
                                          @Param("statut") SessionStatus statut,
                                          Pageable pageable);

    // Get completed audits only (for BI)
    List<AuditSession> findByStatutOrderByDateFinDesc(SessionStatus statut);

    // Get average score by store (for BI)
    @Query("SELECT s.store.nom, AVG(s.noteGlobale) FROM AuditSession s WHERE s.statut = 'TERMINE' GROUP BY s.store.id")
    List<Object[]> getAverageScoreByStore();

    // Get average score by auditor (for BI)
    @Query("SELECT CONCAT(s.auditeur.nom, ' ', s.auditeur.prenom), AVG(s.noteGlobale) FROM AuditSession s WHERE s.statut = 'TERMINE' GROUP BY s.auditeur.id")
    List<Object[]> getAverageScoreByAuditeur();

    // Get average score by region (for BI)
    @Query("SELECT s.store.region, AVG(s.noteGlobale) FROM AuditSession s WHERE s.statut = 'TERMINE' AND s.store.region IS NOT NULL GROUP BY s.store.region")
    List<Object[]> getAverageScoreByRegion();

    // Get monthly audit count (for BI)
    @Query("SELECT FUNCTION('DATE_FORMAT', s.dateCreation, '%Y-%m'), COUNT(s) FROM AuditSession s GROUP BY FUNCTION('DATE_FORMAT', s.dateCreation, '%Y-%m') ORDER BY FUNCTION('DATE_FORMAT', s.dateCreation, '%Y-%m') DESC")
    List<Object[]> getMonthlyAuditCount();
}