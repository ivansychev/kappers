package ru.kappers.service;

import ru.kappers.model.Fixture;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс сервиса спортивных событий
 */
public interface FixtureService {
    Fixture addRecord(Fixture fixture);
    void deleteRecord(Fixture id);
    void deleteRecordByFixtureId(int fixture_id);
    Fixture updateFixture (Fixture fixture);
    List<Fixture> getAll();
    List<Fixture> getFixturesByPeriod(Timestamp from, Timestamp to);
    List<Fixture> getFixturesByPeriod(LocalDateTime from, LocalDateTime to);
    List<Fixture> getFixturesByPeriod(LocalDateTime from, LocalDateTime to, String filter);
    List<Fixture> getFixturesToday();
    List<Fixture> getFixturesToday(String filter);
    List<Fixture> getFixturesLastWeek();
    List<Fixture> getFixturesLastWeek(String filter);
    List<Fixture> getFixturesNextWeek();
    List<Fixture> getFixturesNextWeek(String filter);
    Fixture getById(int id);
}
