package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.leonmodels.LeagueLeon;
import ru.kappers.repository.LeagueLeonRepository;
import ru.kappers.service.LeagueLeonService;

@Service
@Slf4j
public class LeagueLeonServiceImpl implements LeagueLeonService {
    @Autowired
    LeagueLeonRepository repository;
    @Override
    public LeagueLeon getByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public LeagueLeon save(LeagueLeon league) {
        return repository.save(league);
    }
}
