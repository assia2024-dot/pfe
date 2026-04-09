package com.pfe.auditqualiteRITS.entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "magasins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Magasin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false)
    private String ville;

    @Column(nullable = false)
    private Boolean actif = Boolean.TRUE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    @JsonIgnore
    private Region region;

    @OneToMany(mappedBy = "magasin", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Audit> audits;

    @OneToMany(mappedBy = "magasin", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Mission> missions;
}
