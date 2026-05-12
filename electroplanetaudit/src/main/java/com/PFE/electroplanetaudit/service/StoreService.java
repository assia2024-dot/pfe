package com.PFE.electroplanetaudit.service;

import com.PFE.electroplanetaudit.entity.Store;
import com.PFE.electroplanetaudit.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;

    // Create new store (code is required from frontend)
    public Store create(Store store) {
        store.setDateCreation(LocalDateTime.now());
        store.setActif(true);
        return storeRepository.save(store);
    }

    // Get all stores (for export to BI)
    @Transactional(readOnly = true)
    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    // Get store by ID
    @Transactional(readOnly = true)
    public Store findById(Long id) {
        return storeRepository.findById(id).orElse(null);
    }

    // Get store by code
    @Transactional(readOnly = true)
    public Store findByCode(String code) {
        return storeRepository.findByCode(code).orElse(null);
    }

    // Get active stores only
    @Transactional(readOnly = true)
    public List<Store> findActiveStores() {
        return storeRepository.findByActifTrue();
    }

    // Search by keyword (for search bar)
    @Transactional(readOnly = true)
    public List<Store> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return storeRepository.findAll();
        }
        return storeRepository.searchByKeyword(keyword);
    }

    // Get stores with filters and pagination (for store list page)
    @Transactional(readOnly = true)
    public Page<Store> getStoresWithFilters(String keyword, Boolean actif, Pageable pageable) {
        return storeRepository.findAllWithFilters(keyword, actif, pageable);
    }

    // Get all regions (for filter dropdown)
    @Transactional(readOnly = true)
    public List<String> getAllRegions() {
        return storeRepository.findAllRegions();
    }

    // Get all villes (for filter dropdown)
    @Transactional(readOnly = true)
    public List<String> getAllVilles() {
        return storeRepository.findAllVilles();
    }

    // Get store statistics for BI
    @Transactional(readOnly = true)
    public List<Object[]> getStoresByRegion() {
        return storeRepository.countStoresByRegion();
    }

    // FULL UPDATE - admin can edit all fields
    public Store update(Long id, Store updatedStore) {
        Store existing = storeRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        // Update all fields
        if (updatedStore.getCode() != null) existing.setCode(updatedStore.getCode());
        if (updatedStore.getNom() != null) existing.setNom(updatedStore.getNom());
        if (updatedStore.getAdresse() != null) existing.setAdresse(updatedStore.getAdresse());
        if (updatedStore.getVille() != null) existing.setVille(updatedStore.getVille());
        if (updatedStore.getRegion() != null) existing.setRegion(updatedStore.getRegion());
        if (updatedStore.getActif() != null) existing.setActif(updatedStore.getActif());

        return storeRepository.save(existing);
    }

    // Delete store
    public boolean delete(Long id) {
        if (!storeRepository.existsById(id)) {
            return false;
        }
        storeRepository.deleteById(id);
        return true;
    }
}