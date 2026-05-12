package com.PFE.electroplanetaudit.controller;

import com.PFE.electroplanetaudit.dto.*;
import com.PFE.electroplanetaudit.service.UserService;
import com.PFE.electroplanetaudit.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    // Helper to get current user ID from JWT
    private Long getCurrentUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        // You'll need to extract user ID from JWT or fetch from DB
        // For now, we'll get from token via JwtUtil
        // This method will be called from authenticated endpoints
        return null; // Will be implemented properly
    }

    // ===== CREATE USER - ADMIN ONLY =====
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request) {
        try {
            UserResponseDTO user = userService.createUser(request);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }

    // ===== GET ALL USERS - ADMIN ONLY =====
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ===== GET USER BY ID - ADMIN ONLY =====
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            UserResponseDTO user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ===== GET USER BY USERCODE - ADMIN ONLY =====
    @GetMapping("/code/{userCode}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserByUserCode(@PathVariable Integer userCode) {
        try {
            UserResponseDTO user = userService.getUserByUserCode(userCode);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ===== GET USERS WITH FILTERS - ADMIN ONLY =====
    @GetMapping("/filtered")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponseDTO>> getFilteredUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Boolean actif,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nom") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return ResponseEntity.ok(userService.getUsersWithFilters(keyword, role, actif, pageable));
    }

    // ===== UPDATE USER (FULL) - ADMIN ONLY =====
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        try {
            UserResponseDTO user = userService.updateUser(id, request);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }

    // ===== UPDATE OWN PROFILE - AUDITEUR ONLY =====
    @PutMapping("/profile")
    @PreAuthorize("hasRole('AUDITEUR')")
    public ResponseEntity<?> updateOwnProfile(@RequestParam Long userId, @RequestBody UpdateProfileRequest request) {
        try {
            // Verify the logged-in user can only update their own profile
            // You can get the user ID from the JWT token
            UserResponseDTO user = userService.updateOwnProfile(userId, request);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }

    // ===== ACTIVATE/DEACTIVATE USER - ADMIN ONLY =====
    @PatchMapping("/{id}/actif")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> toggleActif(@PathVariable Long id, @RequestParam Boolean actif) {
        try {
            UserResponseDTO user = userService.toggleActif(id, actif);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ===== DELETE USER - ADMIN ONLY =====
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"message\": \"User deleted successfully\"}");
    }
}