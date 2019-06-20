package ru.kappers.service;

import ru.kappers.model.*;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

/**
 * Интерфейс сервиса пользователей
 */
public interface UserService {
    /**
     * Добавить пользователя
     * @param user пользователь
     * @return сохраненный пользователь
     */
    User addUser(User user);

    /**
     * Удалить пользователя по его логину
     * @param userName логин
     */
    void deleteByUserName(String userName);

    /**
     * Удалить пользователя
     * @param user пользователь
     */
    void delete(User user);

    /**
     * Получить пользователя по логину
     * @param name логин
     * @return найденный пользователь или null
     */
    User getByUserName(String name);

    /**
     * Получить пользователя по имени
     * @param name имя
     * @return найденный пользователь или null
     */
    User getByName(String name);

    /**
     * Получить пользователя по id
     * @param id идентификатор
     * @return найденный пользователь или null
     */
    User getById(int id);

    /**
     * Изменить/обновить пользователя
     * @param user пользователь
     * @return сохраненный пользователь
     */
    User editUser(User user);
    List<User> getAll();
    List<User> getAllByRole(String roleName);
    boolean hasRole(User user, String roleName);
    boolean hasRole(User user, int roleId);
    boolean hasRole(User user, Role role);
    Role getRole(User user);
    List<History> getHistory(User user);
    Stat getStat(User user);
    KapperInfo getKapperInfo(User user);
    PersonalInfo getInfo(User user);
    void transfer (User user, User kapper, BigDecimal amount) throws MalformedURLException, ParseException;
    //TODO getEventList, getActiveEvents etc... когда евенты сделаем
}
