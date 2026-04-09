package com.pfe.auditqualiteRITS.entite;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String description;

    private Integer note;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private List<Audit> audits;

    @OneToMany(mappedBy = "categorie", fetch = FetchType.LAZY)
    private List<PieceJointe> piecesJointes;
}
