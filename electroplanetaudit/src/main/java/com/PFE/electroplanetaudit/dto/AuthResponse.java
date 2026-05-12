package com.PFE.electroplanetaudit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private Long userId;
    private Integer userCode;
    private String nom;
    private String prenom;
    private String email;
    private String role;

    public AuthResponse(String token, Long userId, Integer userCode, String nom, String prenom, String email, String role) {
        this.token = token;
        this.userId = userId;
        this.userCode = userCode;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
    }
}