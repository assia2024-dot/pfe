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

    // ONE method for finding by email, code, type, used=false, and not expired
    Optional<TwoFACode> findByEmailAndCodeAndTypeAndUsedFalseAndExpirationAfter(
            String email, String code, String type, LocalDateTime now
    );

    @Modifying
    @Transactional
    @Query("DELETE FROM TwoFACode t WHERE t.email = :email AND t.type = :type AND (t.used = true OR t.expiration < :now)")
    void deleteOldCodes(@Param("email") String email, @Param("type") String type, @Param("now") LocalDateTime now);
}