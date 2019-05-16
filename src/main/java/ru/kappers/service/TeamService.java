package ru.kappers.service;

import ru.kappers.model.catalog.Team;

import java.util.List;

/**
 * Интерфейс сервиса команды
 */
public interface TeamService {
    /**
     * Сохранить команду
     * @param team сохраняемая команда
     * @return сохраненная команда
     */
    Team save(Team team);

    /**
     * Удалить команду
     * @param team удаляемая команда
     */
    void delete(Team team);

    /**
     * Получить команду по id
     * @param id идентификатор команды
     * @return найденная команда или {@literal null}
     */
    Team getById(int id);

    /**
     * Получить команду по имени
     * @param name имя команды
     * @return найденная команда или {@literal null}
     */
    Team getByName(String name);

    /**
     * Получить все команды со строкой в имени
     * @param name строка в имени
     * @return список команд
     */
    List<Team> getAllWithNameContains(String name);

    /**
     * Получить все команды
     * @return список команд
     */
    List<Team> getAll();
}
