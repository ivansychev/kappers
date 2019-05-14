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
    @Column(name="name")
    private String name;
    @Column(name="code")
    private String code;
    @Column(name="logo")
    private String logo;
}
