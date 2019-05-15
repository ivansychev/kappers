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

/**
 * JPA-сущность для команды
 */
@Slf4j
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team")
public class Team {
    @Id
    @Column(name = "team_id",nullable = false, insertable = false, updatable = false)
    private Integer id;
    /** название команды */
    @Column(name="name")
    @Size(max = 255)
    @NotBlank
    private String name;
    @Column(name="code")
    @Size(max = 8)
    private String code;
    @Column(name="logo")
    @Size(max = 512)
    private String logo;
}
