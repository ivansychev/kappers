package ru.kappers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kappers.model.leonmodels.OddsLeon;

public interface OddsLeonRepository extends JpaRepository<OddsLeon, Long>  {
    OddsLeon getByName(String name);
}
