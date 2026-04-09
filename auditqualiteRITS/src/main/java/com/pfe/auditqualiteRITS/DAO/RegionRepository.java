package com.pfe.auditqualiteRITS.DAO;

import com.pfe.auditqualiteRITS.entite.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    Region findByCode(String code);

    boolean existsByCode(String code);
}

