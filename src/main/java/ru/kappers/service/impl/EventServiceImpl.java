package ru.kappers.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.kappers.model.Event;
import ru.kappers.model.User;
import ru.kappers.repository.EventRepository;
import ru.kappers.service.EventService;

import java.util.List;

public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository repository;

    @Override
    public Event addEvent(Event record) {
        return repository.save(record);
    }

    @Override
    public void delete(Event record) {
        repository.delete(record);
    }

    @Override
    public Event getById(int id) {
        return repository.getOne(id);
    }

    @Override
    public List<Event> getUsersEvent(User user) {
        return repository.getByKapper(user);
    }

    @Override
    public List<Event> getAll() {
        return repository.findAll();
    }
}
