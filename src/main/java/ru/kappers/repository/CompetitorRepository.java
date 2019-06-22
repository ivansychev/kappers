package ru.kappers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kappers.model.leonmodels.CompetitorLeon;

public interface CompetitorRepository extends JpaRepository<CompetitorLeon, Long> {
    CompetitorLeon getByName(String name);
}
