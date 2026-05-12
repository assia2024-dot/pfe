package com.PFE.electroplanetaudit.entity;

public enum MissionStatus {
    PLANIFIEE,    // Planned - waiting to start
    EN_COURS,     // In progress - auditor started
    TERMINEE,     // Completed - audit submitted
    ANNULEE       // Cancelled - admin cancelled
}