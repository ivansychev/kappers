package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kappers.exceptions.EntitySaveException;
import ru.kappers.model.leonmodels.OddsLeon;
import ru.kappers.repository.OddsLeonRepository;
import ru.kappers.service.OddsLeonService;

import javax.persistence.EntityNotFoundException;
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
    @Transactional
    public OddsLeon save(OddsLeon odd) {
        OddsLeon newOne = null;
        try {
            newOne = getByName(odd.getName());
            if (newOne == null) return repository.save(odd);
            else return update(odd);
        } catch (Exception e) {
            throw new EntitySaveException("Не удалось сохранить сущность " + odd.getId() + " - " + odd.getName(), e);
        }

    }

    @Override
    public void delete(OddsLeon odd) {
        repository.delete(odd);
    }

    @Override
    public OddsLeon update(OddsLeon odd) {
        try {
            OddsLeon newOne = getByName(odd.getName());
            newOne.setRunners(odd.getRunners());
            newOne.setKickoff(odd.getKickoff());
            newOne.setOpen(odd.isOpen());
            newOne.setLastUpdated(odd.getLastUpdated());
            return repository.save(newOne);
        } catch (Exception e) {
            throw new EntitySaveException("Не удалось сохранить сущность " + odd.getId() + " - " + odd.getName(), e);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public OddsLeon getById(long oddId) {
        return repository.findById(oddId).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OddsLeon> getAll() {
        return repository.findAll();
    }

    @Override
    public OddsLeon getByName(String name) {
        return repository.getByName(name);
    }
}
