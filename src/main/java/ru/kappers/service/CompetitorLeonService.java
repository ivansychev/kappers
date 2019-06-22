package ru.kappers.service;

import ru.kappers.model.leonmodels.CompetitorLeon;

import java.util.List;

public interface CompetitorLeonService {

    CompetitorLeon save(CompetitorLeon comp);

    void delete(CompetitorLeon comp);

    CompetitorLeon update(CompetitorLeon comp);

    CompetitorLeon getById(long compId);

    List<CompetitorLeon> getAll();

    CompetitorLeon getByName(String name);
}
