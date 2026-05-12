package com.PFE.electroplanetaudit.dto;

import com.PFE.electroplanetaudit.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private Integer userCode;
    private String nom;
    private String prenom;
    private String email;
    private Role role;
    private Boolean actif;
    private String telephone;
    private String region;
    private LocalDateTime dateCreation;
}