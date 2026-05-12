package com.PFE.electroplanetaudit.service;

import com.PFE.electroplanetaudit.dto.CreateUserRequest;
import com.PFE.electroplanetaudit.dto.UpdateProfileRequest;
import com.PFE.electroplanetaudit.dto.UpdateUserRequest;
import com.PFE.electroplanetaudit.dto.UserResponseDTO;
import com.PFE.electroplanetaudit.entity.Role;
import com.PFE.electroplanetaudit.entity.User;
import com.PFE.electroplanetaudit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Convert Entity to DTO
    private UserResponseDTO convertToDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .userCode(user.getUserCode())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .role(user.getRole())
                .actif(user.getActif())
                .telephone(user.getTelephone())
                .region(user.getRegion())
                .dateCreation(user.getDateCreation())
                .build();
    }

    // Generate unique userCode
    private Integer generateUserCode() {
        Integer maxCode = userRepository.findMaxUserCode();
        return (maxCode == null) ? 1000 : maxCode + 1;
    }

    // ===== CREATE USER (ADMIN only) =====
    public UserResponseDTO createUser(CreateUserRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        User user = User.builder()
                .userCode(generateUserCode())
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .motDePasse(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .actif(true)
                .telephone(request.getTelephone())
                .region(request.getRegion())
                .dateCreation(LocalDateTime.now())
                .build();

        user = userRepository.save(user);
        return convertToDTO(user);
    }

    // ===== GET ALL USERS (ADMIN only) =====
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ===== GET USER BY ID (ADMIN only) =====
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return convertToDTO(user);
    }

    // ===== GET USER BY USERCODE (ADMIN only) =====
    @Transactional(readOnly =true)
    public UserResponseDTO getUserByUserCode(Integer userCode) {
        User user = userRepository.findByUserCode(userCode)
                .orElseThrow(() -> new RuntimeException("User not found with code: " + userCode));
        return convertToDTO(user);
    }

    // ===== GET USERS WITH FILTERS (ADMIN only) =====
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getUsersWithFilters(String keyword, String role, Boolean actif, Pageable pageable) {
        Role roleEnum = null;
        if (role != null && !role.isEmpty()) {
            try {
                roleEnum = Role.valueOf(role.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Invalid role, ignore
            }
        }

        return userRepository.findAllWithFilters(keyword, roleEnum, actif, pageable)
                .map(this::convertToDTO);
    }

    // ===== UPDATE USER (ADMIN only - full update) =====
    public UserResponseDTO updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (request.getNom() != null) user.setNom(request.getNom());
        if (request.getPrenom() != null) user.setPrenom(request.getPrenom());
        if (request.getEmail() != null) {
            // Check if new email is already used by another user
            if (!user.getEmail().equals(request.getEmail()) &&
                    userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already exists: " + request.getEmail());
            }
            user.setEmail(request.getEmail());
        }
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setMotDePasse(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getTelephone() != null) user.setTelephone(request.getTelephone());
        if (request.getRegion() != null) user.setRegion(request.getRegion());
        if (request.getActif() != null) user.setActif(request.getActif());

        user = userRepository.save(user);
        return convertToDTO(user);
    }

    // ===== UPDATE OWN PROFILE (AUDITEUR only) =====
    public UserResponseDTO updateOwnProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getTelephone() != null) user.setTelephone(request.getTelephone());
        if (request.getRegion() != null) user.setRegion(request.getRegion());

        user = userRepository.save(user);
        return convertToDTO(user);
    }

    // ===== DELETE USER (ADMIN only) =====
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    // ===== ACTIVATE/DEACTIVATE USER (ADMIN only) =====
    public UserResponseDTO toggleActif(Long id, Boolean actif) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setActif(actif);
        user = userRepository.save(user);
        return convertToDTO(user);
    }
}