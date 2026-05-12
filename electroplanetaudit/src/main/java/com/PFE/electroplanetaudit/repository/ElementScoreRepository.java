package com.PFE.electroplanetaudit.repository;

import com.PFE.electroplanetaudit.entity.ElementScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ElementScoreRepository extends JpaRepository<ElementScore, Long> {

    // Find scores by audit session
    List<ElementScore> findByAuditSessionId(Long auditSessionId);

    // Find scores by audit element (for BI)
    List<ElementScore> findByAuditElementId(Long auditElementId);

    // Find score for specific session and element
    ElementScore findByAuditSessionIdAndAuditElementId(Long sessionId, Long elementId);

    // Get average score for a specific element (for BI)
    @Query("SELECT AVG(es.score) FROM ElementScore es WHERE es.auditElement.id = :elementId AND es.score IS NOT NULL")
    Double getAverageScoreByElement(@Param("elementId") Long elementId);

    // Get all scores for an audit session with element names (for export)
    @Query("SELECT es.auditElement.nom, es.score, es.commentaire FROM ElementScore es WHERE es.auditSession.id = :sessionId")
    List<Object[]> getScoresWithElementNames(@Param("sessionId") Long sessionId);

    // Get element performance summary (for BI)
    @Query("SELECT e.nom, AVG(es.score), COUNT(es) FROM ElementScore es JOIN es.auditElement e WHERE es.score IS NOT NULL GROUP BY e.id")
    List<Object[]> getElementPerformanceSummary();

    // Count graded vs ungraded
    @Query("SELECT COUNT(es) FROM ElementScore es WHERE es.auditSession.id = :sessionId AND es.score IS NULL")
    int countUngradedBySession(@Param("sessionId") Long sessionId);
}