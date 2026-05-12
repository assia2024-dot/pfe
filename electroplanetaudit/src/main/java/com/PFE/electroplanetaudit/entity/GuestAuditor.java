package com.PFE.electroplanetaudit.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "guest_auditors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestAuditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String telephone;

    @Column(nullable = false)
    private String statut = "EN_ATTENTE";  // EN_ATTENTE, EN_COURS, TERMINE, EXPIRE

    private LocalDateTime dateAcces;

    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();
}