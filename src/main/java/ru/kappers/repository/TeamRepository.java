package ru.kappers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kappers.model.catalog.Team;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для команды
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    /** Найти команду с именем, содержащим указанную строку
     * @param name строка в имени команды
     * @return список команд
     */
    List<Team> findAllByNameContains(String name);

    /** Найти команду с указанным именем
     * @param name имя команды
     * @return {@link Optional} команды
     */
    Optional<Team> findFirstByName(String name);
}
