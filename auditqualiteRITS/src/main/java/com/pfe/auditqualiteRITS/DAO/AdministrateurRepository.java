package com.pfe.auditqualiteRITS.DAO;


import com.pfe.auditqualiteRITS.entite.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> {

    boolean existsByEmail(String email);
}
