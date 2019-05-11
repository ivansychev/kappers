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
public class RunnerDTO {
    private long id;
    private String name;
    private boolean open;
    private int r;
    private int c;
    private List<String> tags;
    private double price;
}
