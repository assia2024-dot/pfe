package com.PFE.electroplanetaudit.repository;

import com.PFE.electroplanetaudit.entity.Role;
import com.PFE.electroplanetaudit.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserCode(Integer userCode);

    boolean existsByEmail(String email);

    boolean existsByUserCode(Integer userCode);

    List<User> findByRole(Role role);


    List<User> findByActifTrue();

    // Search by nom, prenom, email
    @Query("SELECT u FROM User u WHERE " +
            "(COALESCE(:keyword, '') = '' OR " +
            "LOWER(u.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.prenom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:#{#role} IS NULL OR u.role = :#{#role}) " +
            "AND (:actif IS NULL OR u.actif = :actif)")
    Page<User> findAllWithFilters(@Param("keyword") String keyword,
                                  @Param("role") Role role,
                                  @Param("actif") Boolean actif,
                                  Pageable pageable);

    // Get max userCode for auto-generation
    @Query("SELECT MAX(u.userCode) FROM User u")
    Integer findMaxUserCode();
}