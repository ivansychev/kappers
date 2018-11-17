package ru.kappers.logic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kappers.model.Fixture;
import ru.kappers.model.User;
import ru.kappers.service.FixtureService;
import ru.kappers.util.DateUtil;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/rest/fixtures")
public class FixtureController {
    @Autowired
    private FixtureService service;

    @ResponseBody
    @RequestMapping(value = "/nextweek", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fixture> getFixturesNextWeek() {

        //Должен возвращать события со статусом Not Started и датой от сегодняшнего дня в течении 7 дней

        return service.getFixturesNextWeek();
    }

    @ResponseBody
    @RequestMapping(value = "/lastweek", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fixture> getFixturesLastWeek() {
        //Должен возвращать события со статусом отличающемся от Not Started и датой от и на 7 дней ранее
        return service.getFixturesLastWeek();
    }

    @ResponseBody
    @RequestMapping(value = "/today", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fixture> getFixturesToday() {
        //Должен возвращать все события на сегодня
        return service.getFixturesToday();
    }

    @ResponseBody
    @RequestMapping(value = "/period", method = RequestMethod.GET)
    public List<Fixture> getFixturesByPeriod(
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        return service.getFixturesByPeriod(new Timestamp(fromDate.getTime()), new Timestamp(toDate.getTime()));
    }

    @ResponseBody
    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public List<Fixture> getAllFixtures()
    {
        return service.getAll();
    }

}
