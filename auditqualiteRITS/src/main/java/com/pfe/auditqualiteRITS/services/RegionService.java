package com.pfe.auditqualiteRITS.services;

import com.pfe.auditqualiteRITS.DAO.RegionRepository;
import com.pfe.auditqualiteRITS.entite.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RegionService {

    private final RegionRepository regionRepository;

    @Transactional(readOnly = true)
    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Region findById(Long id) {
        return regionRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Region findByCode(String code) {
        return regionRepository.findByCode(code);
    }

    public Region create(Region region) {
        if (region.getNom() == null || region.getNom().isEmpty()) {
            return null;
        }
        if (region.getCode() == null || region.getCode().isEmpty()) {
            return null;
        }
        if (regionRepository.existsByCode(region.getCode())) {
            return null;
        }
        return regionRepository.save(region);
    }

    public Region update(Long id, Region updated) {
        Region existing = regionRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        if (updated.getNom() != null) {
            existing.setNom(updated.getNom());
        }
        if (updated.getCode() != null) {
            existing.setCode(updated.getCode());
        }

        return regionRepository.save(existing);
    }

    public boolean delete(Long id) {
        Region region = regionRepository.findById(id).orElse(null);
        if (region == null) {
            return false;
        }
        regionRepository.delete(region);
        return true;
    }
}