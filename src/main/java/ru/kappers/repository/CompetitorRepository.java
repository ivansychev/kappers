package ru.kappers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kappers.model.leonmodels.CompetitorLeon;

import java.util.List;

public interface CompetitorRepository extends JpaRepository<CompetitorLeon, Long> {
    CompetitorLeon getByName(String name);

    List<CompetitorLeon> findAllByIdIsNotIn(Iterable<Long> ids);
}
