package ru.kappers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kappers.model.leonmodels.MarketLeon;

public interface MarketLeonRepository extends JpaRepository<MarketLeon, Long> {
    MarketLeon getByName(String name);
}
