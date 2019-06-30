package ru.kappers.model.leonmodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ru.kappers.model.mapping.TeamBridge;

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
    @Column(name = "competitor_id", nullable = false, insertable = false, updatable = false)
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
    @JsonIgnore
    private List<OddsLeon> home_odds;

    @OneToMany (mappedBy = "away")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<OddsLeon> away_odds;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "leonCompetitor")
    private TeamBridge teamBridge;
}
