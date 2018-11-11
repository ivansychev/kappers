package ru.kappers.service;

import ru.kappers.model.Fixture;
import ru.kappers.model.Stat;

import java.util.List;

public interface FixtureService {
    Fixture addRecord(Fixture fixture);
    void deleteRecord(Fixture id);
    void deleteRecordByFixtureId(int fixture_id);
    Fixture updateFixture (Fixture fixture);
    List<Fixture> getAll();
}
