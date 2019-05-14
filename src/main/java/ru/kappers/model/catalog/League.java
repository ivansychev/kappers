package ru.kappers.model.catalog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.kappers.model.dto.leon.SportLeonDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Slf4j
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "league")
public class League {
    @Id
    @Column(name = "league_id",nullable = false, insertable = false, updatable = false)
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="country")
    private String country;
    @Column(name="season")
    private String season;
    @Column(name="season_start")
    private Timestamp seasonStart;
    @Column(name="season_end")
    private Timestamp seasonEnd;
    @Column(name="logo")
    private String logoUrl;
}
