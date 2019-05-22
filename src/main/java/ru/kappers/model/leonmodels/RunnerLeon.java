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
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private long id;
    @Column(name = "name")
    @Size(max = 255)
    @NotBlank
    private String name;
    @Column(name = "open")
    private boolean open;
    @ElementCollection
    @Column(name = "tags")
    private List<String> tags;
    @Column(name = "price")
    private double price;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private MarketLeon market;
}
