package ru.kappers.model.leonmodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;
@Slf4j
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "odds_leon")
public class OddsLeon  {
    @Id
    @Column(name = "odd_id", nullable = false, insertable = false, updatable = false)
    private long id;
    @Column(name = "name")
    @Size(max = 255)
    @NotBlank
    private String name;
    @OneToMany(mappedBy = "odd", cascade = CascadeType.ALL)
    private List<CompetitorLeon> competitors;
    @Column(name = "kickoff")
    private Timestamp kickoff;
    @Column(name = "last_updated")
    private Timestamp lastUpdated;
    @OneToOne(mappedBy = "odd", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private LeagueLeon league;
    @Column(name = "open")
    private boolean open;
    @Column(name = "url")
    @Size(max = 512)
    private String url;
    @OneToMany(mappedBy = "odd", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<MarketLeon> markets;
}
