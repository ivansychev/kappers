package ru.kappers.service;

import ru.kappers.model.Fixture;
import ru.kappers.model.Stat;

import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface FixtureService {
    Fixture addRecord(Fixture fixture);
    void deleteRecord(Fixture id);
    void deleteRecordByFixtureId(int fixture_id);
    Fixture updateFixture (Fixture fixture);
    List<Fixture> getAll();
    List<Fixture> getFixturesByPeriod(Timestamp from, Timestamp to);
    List<Fixture> getFixturesToday();
    List<Fixture> getFixturesToday(String filter);
    List<Fixture> getFixturesLastWeek();
    List<Fixture> getFixturesLastWeek(String filter);
    List<Fixture> getFixturesNextWeek();
    List<Fixture> getFixturesNextWeek(String filter);
    Fixture getById(int id);
}
