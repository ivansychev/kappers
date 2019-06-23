package ru.kappers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kappers.model.leonmodels.MarketLeon;
import ru.kappers.model.leonmodels.OddsLeon;
import ru.kappers.model.leonmodels.RunnerLeon;

import java.util.List;

public interface RunnerLeonRepository extends JpaRepository<RunnerLeon, Long> {
    List<RunnerLeon> getByMarketAndOdd(MarketLeon market, OddsLeon odd);
    RunnerLeon getFirstByMarketAndOddAndName(MarketLeon market, OddsLeon odd, String name);
    List<RunnerLeon> getByOdd(OddsLeon odd);
    void deleteAllByOdd(OddsLeon odd);
}
