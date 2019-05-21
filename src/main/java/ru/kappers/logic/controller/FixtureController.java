package ru.kappers.logic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

/*
    @RequestMapping(value = "/nextweek", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fixture> getNextWeek() {
        return service.getFixturesNextWeek(Status.NOT_STARTED);
    }
*/

// пример тестового URL: http://localhost:8080/rest/fixture/nextweek?page=1&size=30&sort=eventDate
    @RequestMapping(value = "/nextweek", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Fixture> getNextWeek(Pageable pageable) {
        return service.getFixturesNextWeek(Status.NOT_STARTED, pageable);
    }

    @RequestMapping(value = "/lastweek", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fixture> getLastWeek() {
        return service.getFixturesLastWeek(Status.MATCH_FINISHED);
    }

/*
    @RequestMapping(value = "/lastweek", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Fixture> getLastWeek(Pageable pageable) {
        return service.getFixturesLastWeek(Status.MATCH_FINISHED, pageable);
    }
*/

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Fixture getFixtureById(@PathVariable int id) {
        return service.getById(id);
    }

    @RequestMapping(value = "/today", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fixture> getFixturesToday() {
        return service.getFixturesToday();
    }

/*
    @RequestMapping(value = "/today", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Fixture> getFixturesToday(Pageable pageable) {
        return service.getFixturesToday(pageable);
    }
*/

    @RequestMapping(value = "/period", method = RequestMethod.GET)
    public List<Fixture> getFixturesByPeriod(
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        return service.getFixturesByPeriod(new Timestamp(fromDate.getTime()), new Timestamp(toDate.getTime()));
    }

/*
    @RequestMapping(value = "/period", method = RequestMethod.GET)
    public Page<Fixture> getFixturesByPeriod(
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            Pageable pageable) {
        return service.getFixturesByPeriod(new Timestamp(fromDate.getTime()), new Timestamp(toDate.getTime()), pageable);
    }
*/
}