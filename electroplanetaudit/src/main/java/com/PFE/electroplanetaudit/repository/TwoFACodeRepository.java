package com.PFE.electroplanetaudit.repository;

import com.PFE.electroplanetaudit.entity.TwoFACode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TwoFACodeRepository extends JpaRepository<TwoFACode, Long> {

    Optional<TwoFACode> findByEmailAndCodeAndUsedFalseAndExpirationAfter(
            String email, String code, LocalDateTime now
    );

    @Modifying  // ← ADD THIS
    @Transactional  // ← ADD THIS
    @Query("DELETE FROM TwoFACode t WHERE t.email = :email AND (t.used = true OR t.expiration < :now)")
    void deleteByEmailAndUsedTrueOrExpirationBefore(@Param("email") String email, @Param("now") LocalDateTime now);
}