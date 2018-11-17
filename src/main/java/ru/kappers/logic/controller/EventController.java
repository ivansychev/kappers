package ru.kappers.logic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kappers.model.Fixture;
import ru.kappers.model.User;
import ru.kappers.model.utilmodel.Odds;
import ru.kappers.service.FixtureService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/rest/events")
public class EventController {
    @Autowired
    private FixtureService service;

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Odds getFixturesByPeriod(
            @PathVariable int id) {
        Fixture fixture = service.getById(id);
        return new Odds(fixture);
    }
}
