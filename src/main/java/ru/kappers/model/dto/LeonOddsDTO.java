package ru.kappers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO сущность спортивных событий с коэфицентами от букмекеров
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeonOddsDTO {
    private Integer id;
}
