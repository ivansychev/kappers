package ru.kappers.service;

import ru.kappers.model.Event;
import ru.kappers.model.User;

import java.util.List;

/**
 * Интерфейс сервиса событий (спортивных)
 */
public interface EventService {
    Event addEvent(Event record);
    void delete(Event record);
    Event getById(int id);
    List<Event> getUsersEvent(User user);
    List<Event> getAll();
}
