package ru.kappers.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.kappers.model.Fixture;
import ru.kappers.model.Fixture.Status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс сервиса спортивных событий
 */
public interface FixtureService {
    Fixture addRecord(Fixture fixture);
    void deleteRecord(Fixture fixture);
    void deleteRecordById(int id);
    Fixture updateFixture (Fixture fixture);
    List<Fixture> getAll();
    Page<Fixture> getAll(Pageable pageable);
    List<Fixture> getFixturesByPeriod(Timestamp from, Timestamp to);
    Page<Fixture> getFixturesByPeriod(Timestamp from, Timestamp to, Pageable pageable);
    List<Fixture> getFixturesByPeriod(LocalDateTime from, LocalDateTime to);
    Page<Fixture> getFixturesByPeriod(LocalDateTime from, LocalDateTime to, Pageable pageable);
    List<Fixture> getFixturesByPeriod(LocalDateTime from, LocalDateTime to, Status filter);
    Page<Fixture> getFixturesByPeriod(LocalDateTime from, LocalDateTime to, Status filter, Pageable pageable);
    List<Fixture> getFixturesToday();
    Page<Fixture> getFixturesToday(Pageable pageable);
    List<Fixture> getFixturesToday(Status filter);
    Page<Fixture> getFixturesToday(Status filter, Pageable pageable);
    List<Fixture> getFixturesLastWeek();
    Page<Fixture> getFixturesLastWeek(Pageable pageable);
    List<Fixture> getFixturesLastWeek(Status filter);
    Page<Fixture> getFixturesLastWeek(Status filter, Pageable pageable);
    List<Fixture> getFixturesNextWeek();
    Page<Fixture> getFixturesNextWeek(Pageable pageable);
    List<Fixture> getFixturesNextWeek(Status filter);
    Page<Fixture> getFixturesNextWeek(Status filter, Pageable pageable);
    Fixture getById(int id);
}
