package ru.kappers.model.dto;

import lombok.*;

/**
 * DTO для приема и трансформации сущности с фронтенда во внутреннюю сущность TeamBridge
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamBridgeDTO {
    private String rapidTeam;
    private String leonCompetitor;
}
