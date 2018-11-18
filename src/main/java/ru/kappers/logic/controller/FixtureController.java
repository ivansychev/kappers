package ru.kappers.logic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kappers.model.Fixture;
import ru.kappers.service.FixtureService;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/rest/fixture")
public class FixtureController {
    @Autowired
    private FixtureService service;

    @ResponseBody
    @RequestMapping(value = "/nextweek", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fixture> getNextWeek() {
        return service.getFixturesNextWeek("Not Started");
    }

    @ResponseBody
    @RequestMapping(value = "/lastweek", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fixture> getLastWeek() {
        return service.getFixturesLastWeek("Match Finished");
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Fixture getNextWeek(@PathVariable int id) {
        return service.getById(id);

    }

    @ResponseBody
    @RequestMapping(value = "/today", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fixture> getFixturesToday() {
        return service.getFixturesToday();
    }

    @ResponseBody
    @RequestMapping(value = "/period", method = RequestMethod.GET)
    public List<Fixture> getFixturesByPeriod(
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        return service.getFixturesByPeriod(new Timestamp(fromDate.getTime()), new Timestamp(toDate.getTime()));
    }
}