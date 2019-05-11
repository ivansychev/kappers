package ru.kappers.logic.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kappers.model.Event;
import ru.kappers.model.Fixture;
import ru.kappers.service.EventService;
import ru.kappers.service.FixtureService;
import ru.kappers.service.KapperInfoService;
import ru.kappers.service.UserService;
import ru.kappers.util.JsonUtil;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping(value = "/rest/api/fixtures")
public class GetFixturesByAPIController {
    //Этот контроллер доступен для вызова только пользователям с ролью ROLE_ADMIN
    @Autowired
    private FixtureService service;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private KapperInfoService kapperService;
/**
 * Метод предназначен для обновления списка спортивных событий
 * - две недели назад от сегодняшнего дня, и две недели вперед - предстоящие
 * */
    @ResponseBody
    @RequestMapping(value = "/twoweeks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fixture> getFixturesLastWeek() {
       log.debug("getFixturesLastWeek()");
        for (long i = -5; i < 5; i++) {
            try {
                JSONObject jsonObject = JsonUtil.loadFixturesByDate(LocalDate.now().plusDays(i));
                Map<Integer, Fixture> fixturesFromJson = JsonUtil.getFixturesFromJson(jsonObject.toString());
                for (Map.Entry<Integer, Fixture> entry : fixturesFromJson.entrySet()) {
                    Fixture value = entry.getValue();
                    value.setId(entry.getKey());
                    Event event = eventService.getById(entry.getKey());
                    service.addRecord(value);
                    if (event!=null){
                        completeEventData(event, value);
                    }
                }
            } catch (UnirestException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private void completeEventData(Event event, Fixture value) {
       //TODO сравнить результат, закрыть евент

    }

    /**
     * Выгрузка всех событий без каких либо фильтров из БД
     * */

    @ResponseBody
    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public List<Fixture> getAllFixtures()
    {
        return service.getAll();
    }



}
