package ru.kappers.model.dto.leon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BetLineLeonDTO {
    private String name;
    private String combination;
}
