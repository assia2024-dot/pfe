package com.PFE.electroplanetaudit.repository;

import com.PFE.electroplanetaudit.entity.GuestLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface GuestLinkRepository extends JpaRepository<GuestLink, Long> {

    Optional<GuestLink> findByToken(String token);

    Optional<GuestLink> findByTokenAndCodeAcces(String token, String codeAcces);

    Optional<GuestLink> findByMissionId(Long missionId);

    @Query("SELECT g FROM GuestLink g WHERE g.token = :token AND g.used = false AND g.expiration > :now")
    Optional<GuestLink> findValidLink(@Param("token") String token, @Param("now") LocalDateTime now);

    boolean existsByMissionIdAndUsedFalse(Long missionId);
}