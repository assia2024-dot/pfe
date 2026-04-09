package com.pfe.auditqualiteRITS.entite;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonIgnore
    private List<Mission> missions;

    @OneToMany(mappedBy = "auditeur", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Audit> audits;
}
