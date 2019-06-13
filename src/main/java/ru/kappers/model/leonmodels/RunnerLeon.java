package ru.kappers.model.leonmodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Slf4j
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "runner_leon")
public class RunnerLeon {
    @Id
    @Column(name = "runner_id", nullable = false, insertable = false, updatable = false)
    private long id;
    @Column(name = "name")
    @Size(max = 255)
    @NotBlank
    private String name;
    @Column(name = "open")
    private boolean open;
    @Column(name = "tags")
    private String tags;
    @Column(name = "price")
    private double price;
    @ManyToOne
    @JoinColumn(name = "market_id", nullable = false)
    private MarketLeon market;
}
