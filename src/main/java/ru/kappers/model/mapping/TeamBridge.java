package ru.kappers.model.mapping;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ru.kappers.model.catalog.Team;
import ru.kappers.model.leonmodels.CompetitorLeon;

import javax.persistence.*;

/**
 * JPA сущность для маппинга команд с Rapid и Leon
 * @author Ashamaz Shomakhov
 */

@Slf4j
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team_bridge")
public class TeamBridge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id", nullable = false)
    @ToString.Exclude
    private Team rapidTeam;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "competitor_id", nullable = false)
    @ToString.Exclude
    private CompetitorLeon leonCompetitor;
}
