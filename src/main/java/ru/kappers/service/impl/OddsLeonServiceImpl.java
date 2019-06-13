package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kappers.model.leonmodels.OddsLeon;
import ru.kappers.repository.OddsLeonRepository;
import ru.kappers.service.OddsLeonService;

@Slf4j
@Service
@Transactional
public class OddsLeonServiceImpl implements OddsLeonService {
    private OddsLeonRepository repository;

    @Autowired
    public void setRepository(OddsLeonRepository repository) {
        this.repository = repository;
    }

    @Override
    public OddsLeon add(OddsLeon odd) {
        return repository.save(odd);
    }
}
