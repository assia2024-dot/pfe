package com.pfe.auditqualiteRITS.entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Long id;

    @Column(name = "date_realisation")
    @JsonIgnore
    private LocalDateTime dateRealisation;

    @Column(name = "note_globale")
    @JsonIgnore
    private Double noteGlobale;

    @Column(nullable = false)
    @JsonIgnore
    private String statut;

    private String commentaire;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    @JsonIgnore
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditeur_id")
    @JsonIgnore
    private Auditeur auditeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magasin_id", nullable = false)
    @JsonIgnore
    private Magasin magasin;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "audit_categories",
        joinColumns = @JoinColumn(name = "audit_id"),
        inverseJoinColumns = @JoinColumn(name = "categorie_id")
    )
    @JsonIgnore
    private List<Categorie> categories;
}
