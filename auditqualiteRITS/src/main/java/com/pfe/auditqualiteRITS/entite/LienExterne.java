package com.pfe.auditqualiteRITS.entite;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "liens_externes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LienExterne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(name = "code_acces", nullable = false)
    private String codeAcces;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private Boolean utilise = Boolean.FALSE;

    private String telephone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrateur_id", nullable = false)
    private Administrateur administrateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magasin_id", nullable = false)
    private Magasin magasin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditeur_externe_id")
    private AuditeurExterne auditeurExterne;
}
