package com.PFE.electroplanetaudit.controller;

import com.PFE.electroplanetaudit.entity.Store;
import com.PFE.electroplanetaudit.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    // ===== CREATE - ADMIN ONLY =====
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody Store store) {
        Store created = storeService.create(store);
        return ResponseEntity.ok(created);
    }

    // ===== READ - Both ADMIN and AUDITEUR =====
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Store>> getAll() {
        return ResponseEntity.ok(storeService.findAll());
    }

    // ===== READ (By ID) - Both =====
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Store> getById(@PathVariable Long id) {
        Store store = storeService.findById(id);
        if (store == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(store);
    }

    // ===== READ (By code) - Both =====
    @GetMapping("/code/{code}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Store> getByCode(@PathVariable String code) {
        Store store = storeService.findByCode(code);
        if (store == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(store);
    }

    // ===== READ (Active stores) - Both =====
    @GetMapping("/active")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Store>> getActiveStores() {
        return ResponseEntity.ok(storeService.findActiveStores());
    }

    // ===== READ (Search) - Both =====
    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Store>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(storeService.search(keyword));
    }

    // ===== READ (Filtered with pagination) - Both =====
    @GetMapping("/filtered")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<Store>> getFiltered(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean actif,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nom") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return ResponseEntity.ok(storeService.getStoresWithFilters(keyword, actif, pageable));
    }

    // ===== READ (Regions for dropdown) - Both =====
    @GetMapping("/regions")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<String>> getRegions() {
        return ResponseEntity.ok(storeService.getAllRegions());
    }

    // ===== READ (Villes for dropdown) - Both =====
    @GetMapping("/villes")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<String>> getVilles() {
        return ResponseEntity.ok(storeService.getAllVilles());
    }

    // ===== READ (Statistics for BI) - Both =====
    @GetMapping("/stats/regions")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Object[]>> getStatsByRegion() {
        return ResponseEntity.ok(storeService.getStoresByRegion());
    }

    // ===== UPDATE - ADMIN ONLY =====
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Store> update(@PathVariable Long id, @RequestBody Store store) {
        Store updated = storeService.update(id, store);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // ===== DELETE - ADMIN ONLY =====
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = storeService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"message\": \"Store deleted successfully\"}");
    }

}