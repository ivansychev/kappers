package ru.kappers.model.dto.rapidapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kappers.model.utilmodel.Outcomes;

import java.math.BigDecimal;

/**
 * DTO для Заведенного события
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    /** Fixture ID */
    private int f_id;
    /** возможный исход */
    private Outcomes outcome;
    /** коэффициент */
    private BigDecimal coefficient;
    /** сколько токенов ставим */
    private int tokens;
    /** какую цену назначаем за открытие евента пользователями */
    private BigDecimal price;
}
