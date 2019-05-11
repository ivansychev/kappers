package ru.kappers.model.dto.rapidapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO сторонней сущности Спортивное событие
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FixtureRapidDTO {
    Integer fixture_id;
    Long event_timestamp;
    String event_date;
    Integer league_id;
    String round;
    Integer homeTeam_id;
    Integer awayTeam_id;
    String homeTeam;
    String awayTeam;
    String status;
    String statusShort;
    Integer goalsHomeTeam;
    Integer goalsAwayTeam;
    String halftime_score;
    String final_score;
    String penalty;
    Integer elapsed;
    Long firstHalfStart;
    Long secondHalfStart;
}
