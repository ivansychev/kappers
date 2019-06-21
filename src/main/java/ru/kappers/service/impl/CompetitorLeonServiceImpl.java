package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.leonmodels.CompetitorLeon;
import ru.kappers.repository.CompetitorRepository;
import ru.kappers.service.CompetitorLeonService;

import java.util.List;

@Slf4j
@Service
public class CompetitorLeonServiceImpl implements CompetitorLeonService {
    private final CompetitorRepository repository;

    @Autowired
    public CompetitorLeonServiceImpl(CompetitorRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompetitorLeon save(CompetitorLeon comp) {
        return repository.save(comp);
    }

    @Override
    public void delete(CompetitorLeon comp) {
        repository.delete(comp);
    }

    @Override
    public CompetitorLeon update(CompetitorLeon comp) {
        return repository.save(comp);
    }

    @Override
    public CompetitorLeon getById(long compId) {
        return repository.getOne(compId);
    }

    @Override
    public List<CompetitorLeon> getAll() {
        return repository.findAll();
    }

    @Override
    public CompetitorLeon getByName(String name) {
        return repository.getByName(name);
    }
}
