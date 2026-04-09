package com.pfe.auditqualiteRITS.entite;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "utilisateurs")
@Inheritance(strategy = InheritanceType.JOINED)
//Lombok
@Data // creer getters et setters
@NoArgsConstructor // pour creer constructeur sans parametres
@AllArgsConstructor //  creer constructeur avec parametres
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // generer id automatiquement
    private Long id;

    @Column(nullable = false) // notblank
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private Boolean actif = Boolean.TRUE;
}
