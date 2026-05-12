package com.PFE.electroplanetaudit.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "audit_missions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MissionStatus statut = MissionStatus.PLANIFIEE;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "auditeur_id", nullable = false)
    private User auditeur;

    @ManyToOne
    @JoinColumn(name = "administrateur_id")
    private User administrateur;

    @ManyToMany
    @JoinTable(
            name = "mission_audit_elements",
            joinColumns = @JoinColumn(name = "mission_id"),
            inverseJoinColumns = @JoinColumn(name = "audit_element_id")
    )
    private List<AuditElement> auditElements = new ArrayList<>();

    @OneToOne(mappedBy = "mission")
    private AuditSession auditSession;

    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();
}