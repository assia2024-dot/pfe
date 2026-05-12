package com.PFE.electroplanetaudit.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String telephone;
    private String region;
    private Boolean actif;
}