package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kappers.model.leonmodels.OddsLeon;
import ru.kappers.repository.OddsLeonRepository;
import ru.kappers.service.OddsLeonService;

import java.util.List;

@Slf4j
@Service
@Transactional
public class OddsLeonServiceImpl implements OddsLeonService {
    private final OddsLeonRepository repository;

    @Autowired
    public OddsLeonServiceImpl(OddsLeonRepository repository) {
        this.repository = repository;
    }

    @Override
    public OddsLeon save(OddsLeon odd) {
        return repository.save(odd);
    }

    @Override
    public void delete(OddsLeon odd) {
        repository.delete(odd);
    }

    @Override
    public OddsLeon update(OddsLeon odd) {
        return save(odd);
    }

    @Override
    @Transactional(readOnly = true)
    public OddsLeon getById(long oddId) {
        return repository.getOne(oddId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OddsLeon> getAll() {
        return repository.findAll();
    }
}
