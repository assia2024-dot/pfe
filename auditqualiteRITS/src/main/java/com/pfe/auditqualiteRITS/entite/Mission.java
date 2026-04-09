package com.pfe.auditqualiteRITS.entite;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "missions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @Column(nullable = false)
    private String statut;

    @Column(nullable = false)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditeur_id")
    @JsonIgnore
    private Auditeur auditeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrateur_id")
    @JsonIgnore
    private Administrateur administrateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magasin_id", nullable = false)
    @JsonIgnore
    private Magasin magasin;

    @OneToOne(mappedBy = "mission", fetch = FetchType.LAZY)
    @JsonIgnore
    private Audit audit;


}
