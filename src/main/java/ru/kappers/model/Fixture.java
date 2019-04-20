package ru.kappers.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA-сущность Спортивное событие
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fixtures")
public class Fixture implements Serializable, Comparable {

//    @Id
////    @GeneratedValue(strategy = GenerationType.AUTO)
////    @Column(name = "id", nullable = false, insertable = false, updatable = false)
////    @EqualsAndHashCode.Exclude
////    private int Id;
    @Id
    @Column(name = "fixture_id",nullable = false, insertable = false, updatable = false)
    Integer id;
    @Column(name="event_timestamp")
    Long eventTimestamp;
    @Column(name="event_date")
    Timestamp eventDate;
    @Column(name="league_id")
    Integer leagueId;
    @Column(name="round")
    String round;
    @Column(name="homeTeam_id")
    Integer homeTeamId;
    @Column(name="awayTeam_id")
    Integer awayTeamId;
    @Column(name="homeTeam")
    String homeTeam;
    @Column(name="awayTeam")
    String awayTeam;
    @Column(name="status")
    String status;
    @Column(name="statusShort")
    String statusShort;
    @Column(name="goalsHomeTeam")
    Integer goalsHomeTeam;
    @Column(name="goalsAwayTeam")
    Integer goalsAwayTeam;
    @Column(name="halftime_score")
    String halftimeScore;
    @Column(name="final_score")
    String finalScore;
    @Column(name="penalty")
    String penalty;
    @Column(name="elapsed")
    Integer elapsed;
    @Column(name="firstHalfStart")
    Long firstHalfStart;
    @Column(name="secondHalfStart")
    Long secondHalfStart;
    @OneToMany(mappedBy = "fixture")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Event> events = new ArrayList<>();

    public String getProperty(String propName) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Field declaredField = this.getClass().getDeclaredField(propName);
        Class<?> targetType = declaredField.getType();
        Object objectValue = targetType.newInstance();
        Object value = declaredField.get(objectValue);
        return String.valueOf(value);
    }

    @Override
    public int compareTo(Object o) {
        return eventTimestamp.compareTo(((Fixture) o).eventTimestamp);
    }
}