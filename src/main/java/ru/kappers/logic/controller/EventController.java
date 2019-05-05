package ru.kappers.logic.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kappers.model.Event;
import ru.kappers.model.Fixture;
import ru.kappers.model.User;
import ru.kappers.model.dto.EventDTO;
import ru.kappers.model.utilmodel.Odds;
import ru.kappers.service.EventService;
import ru.kappers.service.FixtureService;
import ru.kappers.service.UserService;

@Slf4j
@RestController
@RequestMapping(value = "/rest/events")
public class EventController {
    private FixtureService fService;
    private EventService eService;
    private UserService userService;
    private ConversionService conversionService;

    public static final Gson GSON = new Gson();

    @Autowired
    public void setfService(FixtureService fService) {
        this.fService = fService;
    }

    @Autowired
    public void seteService(EventService eService) {
        this.eService = eService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Odds getFixtureById(@PathVariable int id) {
        log.debug("getFixtureById(id: {})...", id);
        Fixture fixture = fService.getById(id);
        return new Odds(fixture);
    }
/**
 * Создать {@link Event} от имени текущего пользователя
 * Пример JSON для создания евента:
 *
 * {
 * 	"outcome":"GUESTTEAMWIN", //в этом случае ставка на гостевую команду
 * 	"coefficient":"1.35", //кэф пока берем от балды
 * 	"tokens":"50", //сколько токенов ставим
 * 	"price":"500",  //какую цену назначаем за открыте евента юзерами
 * 	"f_id":"37743" //айди фиксчи
 * }
 *
 * */
    @RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Event createEvent(@RequestBody String content) {
        log.debug("createEvent(content: {})...", content);
        EventDTO eventDTO = GSON.fromJson(content, EventDTO.class);
        Event event = conversionService.convert(eventDTO, Event.class);
        User u = userService.getByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        return eService.createEventByUser(event, u);
    }
}
