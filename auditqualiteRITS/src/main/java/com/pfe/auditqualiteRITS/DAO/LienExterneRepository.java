package com.pfe.auditqualiteRITS.DAO;


import com.pfe.auditqualiteRITS.entite.LienExterne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LienExterneRepository extends JpaRepository<LienExterne, Long> {

    LienExterne findByCodeAcces(String codeAcces);

    List<LienExterne> findByMagasinId(Long magasinId);

    List<LienExterne> findByUtilise(Boolean utilise);
}
