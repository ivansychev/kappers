package ru.kappers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kappers.model.catalog.League;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для лиги
 */
@Repository
public interface LeagueRepository extends JpaRepository<League, Integer> {
    /** Найти лигу с именем, содержащим указанную строку
     * @param name строка в имени лиги
     * @return список лиг
     */
    List<League> findAllByNameContains(String name);

    /** Найти лигу с указанным именем
     * @param name имя лиги
     * @return {@link Optional} лиги
     */
    Optional<League> findFirstByName(String name);
}
