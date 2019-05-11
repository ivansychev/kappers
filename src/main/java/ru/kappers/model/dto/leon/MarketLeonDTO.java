package ru.kappers.model.dto.leon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketLeonDTO {
    private long id;
    private String name;
    private List<RunnerLeonDTO> runners;
    private boolean open;
    private String family;
    private String primary;
    private int cols;
    private int rows;
}
