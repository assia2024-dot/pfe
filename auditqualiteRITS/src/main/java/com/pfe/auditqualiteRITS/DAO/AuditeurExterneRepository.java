package com.pfe.auditqualiteRITS.DAO;

import com.pfe.auditqualiteRITS.entite.AuditeurExterne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditeurExterneRepository extends JpaRepository<AuditeurExterne, Long> {

    AuditeurExterne findByNom(String nom);

    List<AuditeurExterne> findByStatut(String statut);
}