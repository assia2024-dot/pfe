package com.PFE.electroplanetaudit.repository;

import com.PFE.electroplanetaudit.entity.AuditMission;
import com.PFE.electroplanetaudit.entity.MissionStatus;
import com.PFE.electroplanetaudit.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;


@Repository
public interface AuditMissionRepository extends JpaRepository<AuditMission, Long> {

    // Find by auditor
    List<AuditMission> findByAuditeur(User auditeur);




    // Find by store
    List<AuditMission> findByStoreId(Long storeId);

    // Find by status (using Enum)
    List<AuditMission> findByStatut(MissionStatus statut);

    // Find by date debut
    List<AuditMission> findByDateDebut(LocalDate date);

    // Find by date range
    List<AuditMission> findByDateDebutBetween(LocalDate start, LocalDate end);

    // Find missions after a certain date
    List<AuditMission> findByDateDebutGreaterThanEqual(LocalDate date);

    // Search by title or description
    @Query("SELECT m FROM AuditMission m WHERE " +
            "LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<AuditMission> searchByKeyword(@Param("keyword") String keyword);

    // Search with pagination and filters (for admin)
    @Query("SELECT m FROM AuditMission m WHERE " +
            "(:keyword IS NULL OR " +
            "LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:status IS NULL OR m.statut = :status) " +
            "AND (:storeId IS NULL OR m.store.id = :storeId) " +
            "AND (:auditeurId IS NULL OR m.auditeur.id = :auditeurId)")
    Page<AuditMission> findAllWithFilters(@Param("keyword") String keyword,
                                          @Param("status") MissionStatus status,
                                          @Param("storeId") Long storeId,
                                          @Param("auditeurId") Long auditeurId,
                                          Pageable pageable);

    // Get missions for logged-in auditor (only their own, with filters)
    @Query("SELECT m FROM AuditMission m WHERE m.auditeur.id = :auditeurId " +
            "AND (:status IS NULL OR m.statut = :status)")
    Page<AuditMission> findMissionsByAuditeurId(@Param("auditeurId") Long auditeurId,
                                                @Param("status") MissionStatus status,
                                                Pageable pageable);

    // Count missions by status (for BI)
    @Query("SELECT m.statut, COUNT(m) FROM AuditMission m GROUP BY m.statut")
    List<Object[]> countByStatus();

    // Count missions by store (for BI)
    @Query("SELECT s.nom, COUNT(m) FROM AuditMission m JOIN m.store s GROUP BY s.nom")
    List<Object[]> countByStore();

    // Count missions by auditor (for BI)
    @Query("SELECT CONCAT(u.nom, ' ', u.prenom), COUNT(m) FROM AuditMission m JOIN m.auditeur u GROUP BY u.id")
    List<Object[]> countByAuditeur();

    // Get upcoming missions (dateDebut >= today and status not TERMINEE or ANNULEE)
    @Query("SELECT m FROM AuditMission m WHERE m.dateDebut >= CURRENT_DATE AND m.statut NOT IN ('TERMINEE', 'ANNULEE')")
    List<AuditMission> findUpcomingMissions();
}