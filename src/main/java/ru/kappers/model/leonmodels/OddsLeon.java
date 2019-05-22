package ru.kappers.model.leonmodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
@Slf4j
@Data
@Builder
@Entity
@AllArgsConstructor
@Table(name = "odds_leon")
public class OddsLeon  {
    private long id;
    private String name;
    private List<CompetitorLeon> competitors;
    private long kickoff;
    private long lastUpdated;
    private LeagueLeon league;
    private boolean open;
    private String url;
    private List<MarketLeon> markets;
}
