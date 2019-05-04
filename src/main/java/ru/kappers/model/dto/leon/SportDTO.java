package ru.kappers.model.dto.leon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SportDTO {
    private long id;
    private String name;
    private BetLineDTO betline;
    private int weight;
    private String family;
}
