package ru.kappers.service;

import ru.kappers.model.leonmodels.MarketLeon;

import java.util.List;

public interface MarketLeonService {
    MarketLeon getByName(String name);
    MarketLeon getById (long id);
    List<MarketLeon> getAll();
    MarketLeon save(MarketLeon market);
}
