package com.PFE.electroplanetaudit.repository;

import com.PFE.electroplanetaudit.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    // Find by code (unique)
    Optional<Store> findByCode(String code);

    // Find by nom (exact match)
    List<Store> findByNom(String nom);

    // Find by ville
    List<Store> findByVille(String ville);

    // Find by region
    List<Store> findByRegion(String region);

    // Find active stores only
    List<Store> findByActifTrue();

    // Search by nom, ville, or region (for search bar)
    @Query("SELECT s FROM Store s WHERE " +
            "LOWER(s.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.ville) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.region) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.adresse) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Store> searchByKeyword(@Param("keyword") String keyword);

    // Search with pagination and sorting
    @Query("SELECT s FROM Store s WHERE " +
            "(:keyword IS NULL OR " +
            "LOWER(s.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.ville) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.region) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:actif IS NULL OR s.actif = :actif)")
    Page<Store> findAllWithFilters(@Param("keyword") String keyword,
                                   @Param("actif") Boolean actif,
                                   Pageable pageable);

    // Get all unique regions (for filter dropdown)
    @Query("SELECT DISTINCT s.region FROM Store s WHERE s.region IS NOT NULL ORDER BY s.region")
    List<String> findAllRegions();

    // Get all unique villes (for filter dropdown)
    @Query("SELECT DISTINCT s.ville FROM Store s WHERE s.ville IS NOT NULL ORDER BY s.ville")
    List<String> findAllVilles();

    // Count stores by region (for BI)
    @Query("SELECT s.region, COUNT(s) FROM Store s WHERE s.actif = true GROUP BY s.region")
    List<Object[]> countStoresByRegion();
}