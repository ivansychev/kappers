package ru.kappers.logic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kappers.convert.OddsLeonDTOToOddsLeonConverter;
import ru.kappers.logic.odds.BetParser;
import ru.kappers.logic.odds.LeonBetParser;
import ru.kappers.model.Event;
import ru.kappers.model.Fixture;
import ru.kappers.service.*;
import ru.kappers.model.dto.leon.OddsLeonDTO;
import ru.kappers.model.leonmodels.OddsLeon;
import ru.kappers.service.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/rest/api/fixtures")
public class GetFixturesByAPIController {
    //Этот контроллер доступен для вызова только пользователям с ролью ROLE_ADMIN
    private final FixtureService service;
    private final EventService eventService;
    private final UserService userService;
    private final KapperInfoService kapperService;
    private final JsonService jsonService;

    @Autowired
    public GetFixturesByAPIController(FixtureService service, EventService eventService, UserService userService,
                                      KapperInfoService kapperService, JsonService jsonService) {
        this.service = service;
        this.eventService = eventService;
        this.userService = userService;
        this.kapperService = kapperService;
        this.jsonService = jsonService;
    }


/**
    /**
 * Метод предназначен для обновления списка спортивных событий
 * - две недели назад от сегодняшнего дня, и две недели вперед - предстоящие
 * Внимание! На период разработки выставлено 5 дней до и 5 дней после. Можно менять в своих целях. Перед деплоем нужно выставить 7 в цикле
 * */
    @ResponseBody
    @RequestMapping(value = "/twoweeks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fixture> getFixturesLastWeek() {
/*
* Закомментированный код нужен для проверки сохранения сущностей. Для запуска можно просто нажать на кнопку в админке
* */
        BetParser<OddsLeonDTO> parser = new LeonBetParser();
        List<String> list = parser.loadEventUrlsOfTournament("/events/Soccer/281474976710876-African-Cup-of-Nations");
        List<OddsLeonDTO> eventsWithOdds = parser.getEventsWithOdds(list);
        Converter<OddsLeonDTO, OddsLeon> converter = new OddsLeonDTOToOddsLeonConverter();

        for (OddsLeonDTO dto: eventsWithOdds) {
            OddsLeon odd = converter.convert(dto);
            oddsService.save(odd);
        }

       log.debug("getFixturesLastWeek()");
        for (long i = -5; i < 5; i++) {
            try {
                JSONObject jsonObject = jsonService.loadFixturesByDate(LocalDate.now().plusDays(i));
                Map<Integer, Fixture> fixturesFromJson = jsonService.getFixturesFromJson(jsonObject.toString());
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
       //Этот фарагмент раскоментировать, когда закончится тестирование сохранения сущностей leon
//        for (long i = -5; i < 5; i++) {
//            try {
//                JSONObject jsonObject = JsonUtil.loadFixturesByDate(LocalDate.now().plusDays(i));
//                Map<Integer, Fixture> fixturesFromJson = JsonUtil.getFixturesFromJson(jsonObject.toString());
//                for (Map.Entry<Integer, Fixture> entry : fixturesFromJson.entrySet()) {
//                    Fixture value = entry.getValue();
//                    value.setId(entry.getKey());
//                    Event event = eventService.getById(entry.getKey());
//                    service.addRecord(value);
//                    if (event!=null){
//                        completeEventData(event, value);
//                    }
//                }
//            } catch (UnirestException e) {
//                throw new RuntimeException(e);
//            }
//        }
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
