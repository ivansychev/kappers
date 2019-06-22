package ru.kappers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kappers.model.leonmodels.LeagueLeon;

public interface LeagueLeonRepository extends JpaRepository<LeagueLeon, Long> {
    LeagueLeon getByName(String name);
}
