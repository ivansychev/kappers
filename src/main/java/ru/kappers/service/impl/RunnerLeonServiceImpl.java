package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kappers.model.leonmodels.RunnerLeon;
import ru.kappers.repository.RunnerLeonRepository;
import ru.kappers.service.MarketLeonService;
import ru.kappers.service.OddsLeonService;
import ru.kappers.service.RunnerLeonService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class RunnerLeonServiceImpl implements RunnerLeonService {
    private final RunnerLeonRepository repository;
    private final MarketLeonService marketService;
    private final OddsLeonService oddService;

    @Autowired
    public RunnerLeonServiceImpl(RunnerLeonRepository repository, MarketLeonService marketService, OddsLeonService oddService) {
        this.repository = repository;
        this.marketService = marketService;
        this.oddService = oddService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RunnerLeon> getByMarketAndOdd(long marketId, long oddId) {
        return repository.getByMarketAndOdd(marketService.getById(marketId), oddService.getById(oddId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RunnerLeon> getByOdd(long oddId) {
        return repository.getByOdd(oddService.getById(oddId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RunnerLeon> getAll() {
        return repository.findAll();
    }

    @Override
    public RunnerLeon save(RunnerLeon runner) {
        return repository.save(runner);
    }

    @Override
    public void delete(RunnerLeon runner) {
        repository.delete(runner);
    }

    @Override
    @Transactional(readOnly = true)
    public RunnerLeon getById(long id) {
        return repository.getOne(id);
    }

    @Override
    public void deleteAllByOddId(long oddId) {
        repository.deleteAllByOdd(oddService.getById(oddId));
    }

    @Override
    public List<RunnerLeon> saveAll(List<RunnerLeon> runners) {
       return runners.stream()
               .map(repository::save)
               .collect(Collectors.toList());
    }
}
