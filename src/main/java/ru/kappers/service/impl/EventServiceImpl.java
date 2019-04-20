package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kappers.model.Event;
import ru.kappers.model.User;
import ru.kappers.repository.EventRepository;
import ru.kappers.service.EventService;

import java.util.List;

/**
 * Реализация сервиса событий (спортивных)
 */
@Slf4j
@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository repository;

    @Autowired
    public EventServiceImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event addEvent(Event record) {
        log.debug("addEvent(record: {})...", record);
        return repository.save(record);
    }

    @Override
    public void delete(Event record) {
        log.debug("delete(record: {})...", record);
        repository.delete(record);
    }

    @Override
    @Transactional(readOnly = true)
    public Event getById(int id) {
        log.debug("getById(id: {})...", id);
        return repository.getOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getUsersEvent(User user) {
        log.debug("getUsersEvent(user: {})...", user);
        return repository.getByKapper(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getAll() {
        log.debug("getAll()...");
        return repository.findAll();
    }
}
