package com.PFE.electroplanetaudit.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "audit_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateDebut;  // Automatically set when starting

    private LocalDateTime dateFin;     // Automatically set when submitting

    private Double noteGlobale;  // Calculated automatically

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionStatus statut = SessionStatus.EN_COURS;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "auditeur_id", nullable = false)
    private User auditeur;

    @OneToOne
    @JoinColumn(name = "mission_id", nullable = false)
    private AuditMission mission;  // Link to planned mission

    @OneToMany(mappedBy = "auditSession", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ElementScore> scores = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();
}