package com.pfe.auditqualiteRITS.entite;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "audits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_realisation")
    private LocalDateTime dateRealisation;

    @Column(name = "note_globale")
    private Double noteGlobale;

    @Column(nullable = false)
    private String statut;

    private String commentaire;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditeur_id")
    private Auditeur auditeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magasin_id", nullable = false)
    private Magasin magasin;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "audit_categories",
        joinColumns = @JoinColumn(name = "audit_id"),
        inverseJoinColumns = @JoinColumn(name = "categorie_id")
    )
    private List<Categorie> categories;
}
