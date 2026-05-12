package com.PFE.electroplanetaudit.dto;

import lombok.Data;

@Data
public class GradeRequest {
    private Long elementId;
    private Integer score;  // 1 to 10
    private String commentaire;
}