package ru.kappers.model.dto.rapidapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO сущность команды с API сайта rapidapi.com
 * */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamRapidDTO {
    Integer team_id;
    String name;
    String code;
    String logo;
}
