package com.PFE.electroplanetaudit.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "media_evidences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaEvidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;  // Path or URL to the uploaded image

    @Column(nullable = false)
    private String fileName;  // Original file name

    @Column(nullable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "element_score_id", nullable = false)
    private ElementScore elementScore;  // Link to the graded element
}