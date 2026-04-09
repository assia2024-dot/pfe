package com.pfe.auditqualiteRITS.entite;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "auditeurs")
@PrimaryKeyJoinColumn(name = "utilisateur_id")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Auditeur extends Utilisateur {

    private String telephone;

    private String region;

    @OneToMany(mappedBy = "auditeur", fetch = FetchType.LAZY)
    private List<Mission> missions;

    @OneToMany(mappedBy = "auditeur", fetch = FetchType.LAZY)
    private List<Audit> audits;
}
