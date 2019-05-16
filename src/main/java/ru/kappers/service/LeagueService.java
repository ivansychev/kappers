package ru.kappers.service;

import ru.kappers.model.catalog.League;

import java.util.List;

/**
 * Интерфейс сервиса лиг
 */
public interface LeagueService {

    /**
     * Сохранить лигу
     * @param league сохраняемая лига
     * @return сохраненная лига
     */
    League save(League league);

    /**
     * Удалить лигу
     * @param league удаляемая лига
     */
    void delete(League league);

    /**
     * Получить лигу по id
     * @param id идентификатор лиги
     * @return найденная лига или {@literal null}
     */
    League getById(int id);

    /**
     * Получить лигу по имени
     * @param name имя лиги
     * @return найденная лига или {@literal null}
     */
    League getByName(String name);

    /**
     * Получить все лиги со строкой в имени
     * @param name строка в имени
     * @return список лиг
     */
    List<League> getAllWithNameContains(String name);

    /**
     * Получить все лиги
     * @return список лиг
     */
    List<League> getAll();
}
