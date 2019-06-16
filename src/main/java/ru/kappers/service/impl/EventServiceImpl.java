package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kappers.exceptions.UserNotHaveKapperRoleException;
import ru.kappers.model.Event;
import ru.kappers.model.KapperInfo;
import ru.kappers.model.Role;
import ru.kappers.model.User;
import ru.kappers.repository.EventRepository;
import ru.kappers.service.EventService;
import ru.kappers.service.KapperInfoService;
import ru.kappers.service.MessageTranslator;

import java.util.List;

/**
 * Реализация сервиса событий (спортивных)
 */
@Slf4j
@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository repository;
    private final KapperInfoService kapperService;
    private final MessageTranslator translator;

    @Autowired
    public EventServiceImpl(EventRepository repository, KapperInfoService kapperService, MessageTranslator translator) {
        this.repository = repository;
        this.kapperService = kapperService;
        this.translator = translator;
    }

    @Override
    public Event addEvent(Event record) {
        log.debug("addEvent(record: {})...", record);
        return repository.save(record);
    }

    @Override
    public Event createEventByUser(Event event, User user) {
        log.debug("createEventByUser(event: {}, user: {})...", event, user);
        if (!user.hasRole(Role.Names.KAPPER)) {
            throw new UserNotHaveKapperRoleException(translator.byCode("user.isNotKapper", user.getUserName()));
        }
        KapperInfo kapper = kapperService.getByUser(user);
        if (kapper == null) {
            throw new RuntimeException(translator.byCode("kapperInfo.notFoundForUser", user.getUserName()));
        }
        Integer price = event.getTokens();
        if (kapper.getTokens() - kapper.getBlockedTokens() < price) {
            throw new RuntimeException(translator.byCode("user.noTokensForEventCreation"));
        }
        event.setKapper(user);
        kapper.setBlockedTokens(kapper.getBlockedTokens() + price);
        Event result = addEvent(event);
        kapperService.editKapper(kapper);
        return result;
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
