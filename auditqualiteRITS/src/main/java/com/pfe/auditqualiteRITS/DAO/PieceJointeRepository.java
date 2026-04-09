package com.pfe.auditqualiteRITS.DAO;


import com.pfe.auditqualiteRITS.entite.PieceJointe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PieceJointeRepository extends JpaRepository<PieceJointe, Long> {

    List<PieceJointe> findByCategorieId(Long categorieId);

    List<PieceJointe> findByType(String type);
}
