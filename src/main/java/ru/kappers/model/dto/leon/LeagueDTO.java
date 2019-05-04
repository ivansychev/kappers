package ru.kappers.model.dto.leon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO сущность для парсинга лиг от беттера
 * */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeagueDTO {
    private long id;
    private String name;
    private SportDTO sport;
    private String url;
}
