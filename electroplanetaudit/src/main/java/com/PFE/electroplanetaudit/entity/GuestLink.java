package com.PFE.electroplanetaudit.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "guest_links")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;  // Unique link token (UUID)

    @Column(nullable = false)
    private String codeAcces;  // 6-digit temporary code

    @Column(nullable = false)
    private LocalDateTime expiration;  // Link expiration date

    @Column(nullable = false)
    private Boolean used = false;

    @OneToOne
    @JoinColumn(name = "mission_id", nullable = false)
    private AuditMission mission;

    @OneToOne
    @JoinColumn(name = "guest_auditor_id", nullable = false)
    private GuestAuditor guestAuditor;

    private LocalDateTime dateCreation = LocalDateTime.now();
}