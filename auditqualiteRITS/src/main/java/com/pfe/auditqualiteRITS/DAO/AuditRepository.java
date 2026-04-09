package com.pfe.auditqualiteRITS.DAO;


import com.pfe.auditqualiteRITS.entite.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

    List<Audit> findByStatut(String statut);

    List<Audit> findByAuditeurId(Long auditeurId);

    List<Audit> findByMagasinId(Long magasinId);
}
