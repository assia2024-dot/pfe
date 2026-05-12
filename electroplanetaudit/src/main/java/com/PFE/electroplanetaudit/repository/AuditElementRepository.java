package com.PFE.electroplanetaudit.repository;

import com.PFE.electroplanetaudit.entity.AuditElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditElementRepository extends JpaRepository<AuditElement, Long> {

    // Find by nom
    List<AuditElement> findByNom(String nom);

    // Find active elements only
    List<AuditElement> findByActifTrue();

    // Search by keyword (nom or description)
    @Query("SELECT e FROM AuditElement e WHERE " +
            "LOWER(e.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<AuditElement> searchByKeyword(@Param("keyword") String keyword);

    // Search with pagination and filters
    @Query("SELECT e FROM AuditElement e WHERE " +
            "(:keyword IS NULL OR " +
            "LOWER(e.nom) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:actif IS NULL OR e.actif = :actif)")
    Page<AuditElement> findAllWithFilters(@Param("keyword") String keyword,
                                          @Param("actif") Boolean actif,
                                          Pageable pageable);

    // Count active vs inactive (for BI)
    @Query("SELECT e.actif, COUNT(e) FROM AuditElement e GROUP BY e.actif")
    List<Object[]> countByActifStatus();
}