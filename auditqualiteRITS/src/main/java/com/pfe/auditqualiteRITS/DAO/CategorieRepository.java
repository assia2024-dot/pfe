package com.pfe.auditqualiteRITS.DAO;


import com.pfe.auditqualiteRITS.entite.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    List<Categorie> findByAuditsId(Long auditId);
}
