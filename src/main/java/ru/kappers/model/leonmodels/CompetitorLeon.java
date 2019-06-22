package ru.kappers.model.leonmodels;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Сущность парсинга Competitor из Leon
 * */
@Slf4j
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "competitor_leon")

public class CompetitorLeon  {
    @Id
    @Column(name = "competitor_id", nullable = false, insertable = true, updatable = false)
    private long id;
    @Column(name = "name")
    @Size(max = 255)
    @NotBlank
    private String name;
    @Column(name = "logo")
    @Size(max = 512)
    private String logo;

    @OneToMany (mappedBy = "home")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<OddsLeon> home_odds;

    @OneToMany (mappedBy = "away")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<OddsLeon> away_odds;
}
