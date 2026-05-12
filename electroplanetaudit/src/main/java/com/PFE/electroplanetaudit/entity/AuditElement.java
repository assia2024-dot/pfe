package com.PFE.electroplanetaudit.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_elements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;  // Ex: "Parking", "Sol", "Éclairage"

    @Column(length = 500)
    private String description;  // Description optionnelle

    @Column(nullable = false)
    private Boolean actif = true;

    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();
}