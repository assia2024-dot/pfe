package com.pfe.auditqualiteRITS.entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "administrateurs")
@PrimaryKeyJoinColumn(name = "utilisateur_id")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Administrateur extends Utilisateur {

    @OneToMany(mappedBy = "administrateur", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Mission> missions;

    @OneToMany(mappedBy = "administrateur", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<LienExterne> liensExternes;
}
