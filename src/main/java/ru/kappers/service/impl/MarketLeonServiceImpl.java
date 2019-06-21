package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kappers.model.leonmodels.MarketLeon;
import ru.kappers.repository.MarketLeonRepository;
import ru.kappers.service.MarketLeonService;

import java.util.List;

@Service
@Slf4j
@Transactional
public class MarketLeonServiceImpl implements MarketLeonService {
    private final MarketLeonRepository repository;

    @Autowired
    public MarketLeonServiceImpl(MarketLeonRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public MarketLeon getByName(String name) {
        return repository.getByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public MarketLeon getById(long id) {
        return repository.getOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MarketLeon> getAll() {
        return repository.findAll();
    }

    @Override
    public MarketLeon save(MarketLeon market) {
        return repository.save(market);
    }
}
