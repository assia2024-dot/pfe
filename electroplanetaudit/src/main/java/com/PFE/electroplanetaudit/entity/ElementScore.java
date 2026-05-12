package com.PFE.electroplanetaudit.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "element_scores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElementScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;  // 1 to 10, null if not graded yet

    @Column(length = 500)
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "audit_session_id", nullable = false)
    private AuditSession auditSession;

    @ManyToOne
    @JoinColumn(name = "audit_element_id", nullable = false)
    private AuditElement auditElement;

    @OneToMany(mappedBy = "elementScore", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MediaEvidence> mediaEvidences = new ArrayList<>();
}