package ru.kappers.model.dto.leon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO сущность команд, соревнующихся в евенте
 * */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompetitorLeonDTO {
    private long id;
    private String name;
    private String homeAway;
    private String type;
    private String logo;
}
