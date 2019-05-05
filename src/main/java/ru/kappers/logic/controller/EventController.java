package ru.kappers.logic.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kappers.exceptions.UserNotHaveKapperRoleException;
import ru.kappers.model.Event;
import ru.kappers.model.Fixture;
import ru.kappers.model.KapperInfo;
import ru.kappers.model.User;
import ru.kappers.model.dto.EventDTO;
import ru.kappers.model.utilmodel.Odds;
import ru.kappers.service.EventService;
import ru.kappers.service.FixtureService;
import ru.kappers.service.KapperInfoService;
import ru.kappers.service.UserService;

@Slf4j
@RestController
@RequestMapping(value = "/rest/events")
public class EventController {
    private FixtureService fService;
    private EventService eService;
    private UserService userService;
    private KapperInfoService kapperService;
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
    public void setKapperService(KapperInfoService kapperService) {
        this.kapperService = kapperService;
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
        return createEventByUser(event, u);
    }

    //todo вынести этот метод в EventService, он должен выполняться под транзакцией, т.к. по сути сохраняет 2 сущности одной операцией
    /**
     * Создать {@link Event} от имени указанного пользователя
     * @param event создаваемый {@link Event}
     * @param user пользователь, от имени которого создается {@link Event}
     * @return созданный {@link Event}
     * @throws UserNotHaveKapperRoleException если указанный пользователь не является каппером
     * @throws RuntimeException если не найден экземпляр KapperInfo для указанного пользователя
     * @throws RuntimeException в случае недостаточного баланса токенов у указанного пользователя для создания {@link Event}
     */
    public Event createEventByUser(Event event, User user) {
        log.debug("createEventByUser(event: {}, user: {})...", event, user);
        if (!user.hasRole("ROLE_KAPPER")) {
            throw new UserNotHaveKapperRoleException("The user " + user.getUserName() + " is not kapper");
        }
        KapperInfo kapper = kapperService.getByUser(user);
        if (kapper == null) {
            throw new RuntimeException("KapperInfo was not found for user with username: " + user.getUserName());
        }
        Integer price = event.getTokens();
        if (kapper.getTokens() - kapper.getBlockedTokens() < price) {
            throw new RuntimeException("Недостаточно токенов для создания события");
        }
        event.setKapper(user);
        kapper.setBlockedTokens(kapper.getBlockedTokens() + price);
        Event result = eService.addEvent(event);
        kapperService.editKapper(kapper);
        return result;
    }
}
