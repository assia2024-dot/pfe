package com.pfe.auditqualiteRITS.entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditeurs_externes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditeurExterne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String telephone;

    @Column(nullable = false)
    private String statut;

    @Column(name = "date_acces")
    private LocalDateTime dateAcces;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    @JsonIgnore
    private Mission mission;
}
