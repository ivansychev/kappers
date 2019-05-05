package ru.kappers.service;

import ru.kappers.exceptions.UserNotHaveKapperRoleException;
import ru.kappers.model.Event;
import ru.kappers.model.User;

import java.util.List;

/**
 * Интерфейс сервиса событий (спортивных)
 */
public interface EventService {
    Event addEvent(Event record);
    /**
     * Создать {@link Event} от имени указанного пользователя
     * @param event создаваемый {@link Event}
     * @param user пользователь, от имени которого создается {@link Event}
     * @return созданный {@link Event}
     * @throws UserNotHaveKapperRoleException если указанный пользователь не является каппером
     * @throws RuntimeException если не найден экземпляр KapperInfo для указанного пользователя
     * @throws RuntimeException в случае недостаточного баланса токенов у указанного пользователя для создания {@link Event}
     */
    Event createEventByUser(Event event, User user);
    void delete(Event record);
    Event getById(int id);
    List<Event> getUsersEvent(User user);
    List<Event> getAll();
}
