package ru.kappers.model.catalog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * JPA-сущность для лиги
 */
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
    /** название лиги */
    @Column(name="name")
    @Size(max = 255)
    @NotBlank
    private String name;
    @Column(name="country")
    @Size(max = 180)
    private String country;
    @Column(name="season")
    @Size(max = 9)
    private String season;
    @Column(name="season_start")
    private Timestamp seasonStart;
    @Column(name="season_end")
    private Timestamp seasonEnd;
    @Column(name="logo")
    @Size(max = 512)
    private String logoUrl;
}
