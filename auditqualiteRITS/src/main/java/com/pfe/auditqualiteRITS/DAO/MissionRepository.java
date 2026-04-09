package com.pfe.auditqualiteRITS.DAO;


import com.pfe.auditqualiteRITS.entite.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findByStatut(String statut);

    List<Mission> findByAuditeurId(Long auditeurId);

    List<Mission> findByMagasinId(Long magasinId);
}
