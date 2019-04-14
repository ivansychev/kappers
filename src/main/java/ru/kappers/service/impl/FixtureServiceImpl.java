package ru.kappers.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.Fixture;
import ru.kappers.model.Stat;
import ru.kappers.repository.FixtureRepository;
import ru.kappers.repository.HistoryRepository;
import ru.kappers.service.FixtureService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Log4j
@Service
public class FixtureServiceImpl implements FixtureService {
    @Autowired
    private final FixtureRepository repository;

    public FixtureServiceImpl(FixtureRepository repository) {
        this.repository = repository;
    }

    @Override
    public Fixture addRecord(Fixture fixture) {
        return repository.save(fixture);
    }

    @Override
    public void deleteRecord(Fixture fixture) {
        repository.delete(fixture);
    }

    @Override
    public void deleteRecordByFixtureId(int fixture_id) {
        repository.deleteById(fixture_id);
    }

    @Override
    public Fixture updateFixture(Fixture fixture) {
        repository.save(fixture);
        return null;
    }

    @Override
    public List<Fixture> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Fixture> getFixturesByPeriod(Timestamp from, Timestamp to) {
        return repository.getFixturesByPeriod(from, to);
    }

    @Override
    public List<Fixture> getFixturesToday() {
        Timestamp from = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        Timestamp to = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        return repository.getFixturesByPeriod(from, to);
    }

    @Override
    public List<Fixture> getFixturesToday(String filter) {
        Timestamp from = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        Timestamp to = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        return repository.getFixturesByPeriod(from, to, filter);
    }

    @Override
    public List<Fixture> getFixturesLastWeek() {
        Timestamp to = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        Timestamp from = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).minusDays(7));
        return repository.getFixturesByPeriod(from, to);
    }

    @Override
    public List<Fixture> getFixturesLastWeek(String filter) {
        Timestamp to = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        Timestamp from = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).minusDays(7));
        return repository.getFixturesByPeriod(from, to, filter);
    }

    @Override
    public List<Fixture> getFixturesNextWeek() {
        Timestamp from = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        Timestamp to = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).plusDays(7));
        return repository.getFixturesByPeriod(from, to);
    }

    @Override
    public List<Fixture> getFixturesNextWeek(String filter) {
        Timestamp from = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        Timestamp to = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).plusDays(7));
        return repository.getFixturesByPeriod(from, to, filter);
    }

    @Override
    public Fixture getById(int id) {
        return repository.getById(id);
    }
}
