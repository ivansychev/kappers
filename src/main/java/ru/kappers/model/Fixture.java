package ru.kappers.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ru.kappers.model.dto.FixtureDTO;
import ru.kappers.util.DateTimeUtil;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * JPA-сущность Спортивное событие
 */
@Slf4j
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fixtures")
public class Fixture implements Serializable, Comparable {

    public static Fixture getFixtureFromDTO(FixtureDTO dto){
        Fixture fixture = new Fixture();
        fixture.id = dto.getFixture_id();
        fixture.eventTimestamp = dto.getEvent_timestamp();
        fixture.eventDate = DateTimeUtil.parseTimestampFromDate(dto.getEvent_date());
        fixture.leagueId = dto.getLeague_id();
        fixture.round = dto.getRound();
        fixture.homeTeamId = dto.getHomeTeam_id();
        fixture.awayTeamId = dto.getAwayTeam_id();
        fixture.homeTeam = dto.getHomeTeam();
        fixture.awayTeam = dto.getAwayTeam();
        fixture.status = Status.byValue(dto.getStatus());
        fixture.statusShort = ShortStatus.byValue(dto.getStatusShort());
        fixture.goalsHomeTeam = dto.getGoalsHomeTeam();
        fixture.goalsAwayTeam = dto.getGoalsAwayTeam();
        fixture.halftimeScore = dto.getHalftime_score();
        fixture.finalScore = dto.getFinal_score();
        fixture.penalty = dto.getPenalty();
        fixture.elapsed = dto.getElapsed();
        fixture.firstHalfStart = dto.getFirstHalfStart();
        fixture.secondHalfStart = dto.getSecondHalfStart();
        return fixture;
    }
//    @Id
////    @GeneratedValue(strategy = GenerationType.AUTO)
////    @Column(name = "id", nullable = false, insertable = false, updatable = false)
////    @EqualsAndHashCode.Exclude
////    private int Id;
    @Id
    @Column(name = "fixture_id",nullable = false, insertable = false, updatable = false)
    private Integer id;
    @Column(name="event_timestamp")
    private Long eventTimestamp;
    @Column(name="event_date")
    private Timestamp eventDate;
    @Column(name="league_id")
    private Integer leagueId;
    @Column(name="round")
    private String round;
    @Column(name="homeTeam_id")
    private Integer homeTeamId;
    @Column(name="awayTeam_id")
    private Integer awayTeamId;
    @Column(name="homeTeam")
    private String homeTeam;
    @Column(name="awayTeam")
    private String awayTeam;
    /** Статус */
    @Column(name="status")
    @Convert(converter = StatusConverter.class)
    private Status status;
    /** Сокращенный статус */
    @Column(name="statusShort")
    @Convert(converter = ShortStatusConverter.class)
    private ShortStatus statusShort;
    @Column(name="goalsHomeTeam")
    private Integer goalsHomeTeam;
    @Column(name="goalsAwayTeam")
    private Integer goalsAwayTeam;
    @Column(name="halftime_score")
    private String halftimeScore;
    @Column(name="final_score")
    private String finalScore;
    @Column(name="penalty")
    private String penalty;
    @Column(name="elapsed")
    private Integer elapsed;
    @Column(name="firstHalfStart")
    private Long firstHalfStart;
    @Column(name="secondHalfStart")
    private Long secondHalfStart;
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

    /**
     * Статус спортивного события
     */
    @Getter
    @AllArgsConstructor
    @ToString
    public enum Status {
        /** Спортивное событие не началось */
        NOT_STARTED("Not Started"),
        /** Матч закончен */
        MATCH_FINISHED("Match Finished");

        /** Значение статуса */
        private final String value;

        /**
         * Найти статус по значению
         * @param value значение статуса
         * @return найденный статус или {@literal null}
         */
        @Nullable
        public static Status byValue(String value) {
            for (Status val : Status.values()) {
                if (Objects.equals(value, val.value)) {
                    return val;
                }
            }
            return null;
        }
    }

    /**
     * Сокращенный статус спортивного события
     */
    @Getter
    @AllArgsConstructor
    @ToString
    public enum ShortStatus {
        /** Спортивное событие не началось */
        NOT_STARTED("NS"),
        /** Матч закончен */
        MATCH_FINISHED("FT");

        /** Значение статуса */
        private final String value;

        /**
         * Найти статус по значению
         * @param value значение статуса
         * @return найденный статус или {@literal null}
         */
        @Nullable
        public static ShortStatus byValue(String value) {
            for (ShortStatus val : ShortStatus.values()) {
                if (Objects.equals(value, val.value)) {
                    return val;
                }
            }
            return null;
        }
    }

    @Converter
    public static class StatusConverter implements AttributeConverter<Status, String> {
        @Override
        public String convertToDatabaseColumn(Status attribute) {
            if (attribute == null) {
                return null;
            }
            return attribute.getValue();
        }

        @Override
        public Status convertToEntityAttribute(String dbData) {
            return Status.byValue(dbData);
        }
    }

    @Converter
    public static class ShortStatusConverter implements AttributeConverter<ShortStatus, String> {
        @Override
        public String convertToDatabaseColumn(ShortStatus attribute) {
            if (attribute == null) {
                return null;
            }
            return attribute.getValue();
        }

        @Override
        public ShortStatus convertToEntityAttribute(String dbData) {
            return ShortStatus.byValue(dbData);
        }
    }
}