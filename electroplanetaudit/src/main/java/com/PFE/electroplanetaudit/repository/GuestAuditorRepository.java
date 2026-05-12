package com.PFE.electroplanetaudit.repository;

import com.PFE.electroplanetaudit.entity.GuestAuditor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GuestAuditorRepository extends JpaRepository<GuestAuditor, Long> {

    Optional<GuestAuditor> findByTelephone(String telephone);

    List<GuestAuditor> findByStatut(String statut);
}