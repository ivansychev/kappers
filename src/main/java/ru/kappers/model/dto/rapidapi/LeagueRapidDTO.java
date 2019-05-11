package ru.kappers.model.dto.rapidapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
* DTO сущность лиги с API сайта rapidapi.com
* */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeagueRapidDTO {
    Integer league_id;
    String name;
    String country;
    String season;
    String season_start;
    String season_end;
    String logo;
    Boolean standings;
}
