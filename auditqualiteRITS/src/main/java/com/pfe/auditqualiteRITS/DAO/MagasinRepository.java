package com.pfe.auditqualiteRITS.DAO;


import com.pfe.auditqualiteRITS.entite.Magasin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MagasinRepository extends JpaRepository<Magasin, Long> {

    List<Magasin> findByVille(String ville);

    List<Magasin> findByRegionId(Long regionId);

    List<Magasin> findByActif(Boolean actif);
}
