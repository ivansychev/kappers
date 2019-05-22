package ru.kappers.model.leonmodels;

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
    @Column(name = "id",nullable = false, insertable = false, updatable = false)
    private long id;
    @Column(name = "name")
    @Size(max = 255)
    @NotBlank
    private String name;
    @Size(max = 8)
    @Column(name = "home_away")
    private String homeAway;
    @Column(name = "type")
    @Size(max = 32)
    private String type;
    @Column(name = "logo")
    @Size(max = 512)
    private String logo;
}
