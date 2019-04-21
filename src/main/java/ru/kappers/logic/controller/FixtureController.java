package ru.kappers.logic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.kappers.model.Fixture;
import ru.kappers.model.Fixture.Status;
import ru.kappers.service.FixtureService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/fixture")
public class FixtureController {
    private final FixtureService service;

    @Autowired
    public FixtureController(FixtureService service) {
        this.service = service;
    }

    @RequestMapping(value = "/nextweek", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fixture> getNextWeek() {
        return service.getFixturesNextWeek(Status.NOT_STARTED);
    }

    @RequestMapping(value = "/lastweek", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fixture> getLastWeek() {
        return service.getFixturesLastWeek(Status.MATCH_FINISHED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Fixture getNextWeek(@PathVariable int id) {
        return service.getById(id);
    }

    @RequestMapping(value = "/today", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fixture> getFixturesToday() {
        return service.getFixturesToday();
    }

    @RequestMapping(value = "/period", method = RequestMethod.GET)
    public List<Fixture> getFixturesByPeriod(
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        return service.getFixturesByPeriod(new Timestamp(fromDate.getTime()), new Timestamp(toDate.getTime()));
    }
}