package ru.kappers.model.leonmodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Slf4j
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "league_leon")
public class LeagueLeon {
    @Id
    @Column(name = "league_id", nullable = false, insertable = false, updatable = false)
    private long id;
    @Column(name = "name")
    @Size(max = 255)
    @NotBlank
    private String name;
    @ManyToOne
    @JoinColumn(name = "sport_id", nullable = false)
    private SportLeon sport;
    @Column(name = "url")
    @Size(max = 512)
    private String url;
    @JoinColumn(name = "odd_id")
    @OneToOne(cascade = CascadeType.ALL)
    private OddsLeon odd;
}
