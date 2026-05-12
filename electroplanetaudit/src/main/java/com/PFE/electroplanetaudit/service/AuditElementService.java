package com.PFE.electroplanetaudit.service;

import com.PFE.electroplanetaudit.entity.AuditElement;
import com.PFE.electroplanetaudit.repository.AuditElementRepository;
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
public class AuditElementService {

    private final AuditElementRepository auditElementRepository;

    // CREATE
    public AuditElement create(AuditElement element) {
        element.setDateCreation(LocalDateTime.now());
        element.setActif(true);
        return auditElementRepository.save(element);
    }

    // READ - All
    @Transactional(readOnly = true)
    public List<AuditElement> findAll() {
        return auditElementRepository.findAll();
    }

    // READ - By ID
    @Transactional(readOnly = true)
    public AuditElement findById(Long id) {
        return auditElementRepository.findById(id).orElse(null);
    }

    // READ - Active only
    @Transactional(readOnly = true)
    public List<AuditElement> findActiveElements() {
        return auditElementRepository.findByActifTrue();
    }

    // READ - Search
    @Transactional(readOnly = true)
    public List<AuditElement> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return auditElementRepository.findAll();
        }
        return auditElementRepository.searchByKeyword(keyword);
    }

    // READ - With filters and pagination
    @Transactional(readOnly = true)
    public Page<AuditElement> getElementsWithFilters(String keyword, Boolean actif, Pageable pageable) {
        return auditElementRepository.findAllWithFilters(keyword, actif, pageable);
    }

    // READ - Statistics for BI
    @Transactional(readOnly = true)
    public List<Object[]> getStatsByActifStatus() {
        return auditElementRepository.countByActifStatus();
    }

    // UPDATE - Full update
    public AuditElement update(Long id, AuditElement updatedElement) {
        AuditElement existing = auditElementRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        if (updatedElement.getNom() != null) existing.setNom(updatedElement.getNom());
        if (updatedElement.getDescription() != null) existing.setDescription(updatedElement.getDescription());
        if (updatedElement.getActif() != null) existing.setActif(updatedElement.getActif());

        return auditElementRepository.save(existing);
    }

    // DELETE
    public boolean delete(Long id) {
        if (!auditElementRepository.existsById(id)) {
            return false;
        }
        auditElementRepository.deleteById(id);
        return true;
    }
}