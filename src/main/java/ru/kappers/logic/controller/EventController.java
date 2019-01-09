package ru.kappers.logic.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kappers.model.Event;
import ru.kappers.model.Fixture;
import ru.kappers.model.KapperInfo;
import ru.kappers.model.User;
import ru.kappers.model.utilmodel.Odds;
import ru.kappers.model.utilmodel.Outcomes;
import ru.kappers.service.EventService;
import ru.kappers.service.FixtureService;
import ru.kappers.service.KapperInfoService;
import ru.kappers.service.UserService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/rest/events")
public class EventController {
    @Autowired
    private FixtureService fService;
    @Autowired
    private EventService eService;
    @Autowired
    private UserService userService;
    @Autowired
    private KapperInfoService kapperService;

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Odds getFixtureById(
            @PathVariable int id) {
        Fixture fixture = fService.getById(id);
        return new Odds(fixture);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Event createEvent(@RequestBody String content) {
        Gson gson = new Gson();
        JsonObject jObject = gson.fromJson(content, JsonElement.class).getAsJsonObject();
        Event event = gson.fromJson(jObject, Event.class);
        User u = userService.getByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        KapperInfo kapper = kapperService.getByUser(u);
        if (u.hasRole("ROLE_KAPPER") &&
                kapper != null &&
                kapper.getTokens() >= event.getTokens()) {
            event.setKapper(u);
            Fixture fixture = fService.getById(jObject.get("f_id").getAsInt());
            event.setFixture(fixture);
            Event result = eService.addEvent(event);
            return result;
        } else {
            throw new IllegalArgumentException("The user " + u.getUserName() + " is not kapper");
        }
    }
}
