package ru.kappers.model;


import lombok.*;
import lombok.extern.log4j.Log4j;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Log4j
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
    int fixture_id;
    @Column(name="event_timestamp")
    Long event_timestamp;
    @Column(name="event_date")
    String event_date;
    @Column(name="league_id")
    int league_id;
    @Column(name="round")
    String round;
    @Column(name="homeTeam_id")
    int homeTeam_id;
    @Column(name="awayTeam_id")
    int awayTeam_id;
    @Column(name="homeTeam")
    String homeTeam;
    @Column(name="awayTeam")
    String awayTeam;
    @Column(name="status")
    String status;
    @Column(name="statusShort")
    String statusShort;
    @Column(name="goalsHomeTeam")
    int goalsHomeTeam;
    @Column(name="goalsAwayTeam")
    int goalsAwayTeam;
    @Column(name="halftime_score")
    String halftime_score;
    @Column(name="final_score")
    String final_score;
    @Column(name="penalty")
    String penalty;
    @Column(name="elapsed")
    int elapsed;
    @Column(name="firstHalfStart")
    Long firstHalfStart;
    @Column(name="secondHalfStart")
    Long secondHalfStart;

   @Override
    public int compareTo(Object o) {
        return (getEvent_timestamp()).compareTo(((Fixture) o).getEvent_timestamp());
    }
}