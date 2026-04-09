package com.pfe.auditqualiteRITS.DAO;

import com.pfe.auditqualiteRITS.entite.Auditeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditeurRepository extends JpaRepository<Auditeur, Long> {

    List<Auditeur> findByRegion(String region);

    boolean existsByEmail(String email);
}
