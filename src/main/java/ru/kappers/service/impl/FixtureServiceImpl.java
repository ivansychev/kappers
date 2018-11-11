package ru.kappers.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.Fixture;
import ru.kappers.model.Stat;
import ru.kappers.repository.FixtureRepository;
import ru.kappers.repository.HistoryRepository;
import ru.kappers.service.FixtureService;

import java.util.List;

@Log4j
@Service
public class FixtureServiceImpl implements FixtureService {
    private final FixtureRepository repository;

    @Autowired
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
       // repositorydeleteByFixture_id(fixture_id);
    }

    @Override
    public Fixture updateFixture(Fixture fixture) {
        repository.save(fixture);
        return null;
    }

    @Override
    public List<Fixture> getAll() {
        return (List<Fixture>) repository.findAll();
    }
}
