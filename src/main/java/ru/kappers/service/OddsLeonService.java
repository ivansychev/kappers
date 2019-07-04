package ru.kappers.service;

import ru.kappers.model.leonmodels.OddsLeon;

import java.util.List;

public interface OddsLeonService {
    OddsLeon save(OddsLeon odd);

    void delete(OddsLeon odd);

    OddsLeon update(OddsLeon odd);

    OddsLeon getById(long oddId);

    List<OddsLeon> getAll();

    OddsLeon getByName(String name);
}
