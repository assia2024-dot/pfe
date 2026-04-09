package com.pfe.auditqualiteRITS.DAO;

import com.pfe.auditqualiteRITS.entite.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Utilisateur findByEmail(String email);

    boolean existsByEmail(String email);
}
