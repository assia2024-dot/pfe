package com.PFE.electroplanetaudit.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "two_fa_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TwoFACode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String code;  // 6-digit code

    @Column(nullable = false)
    private LocalDateTime expiration;

    @Column(nullable = false)
    private Boolean used = false;
}