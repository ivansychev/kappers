package ru.kappers.logic.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kappers.model.Fixture;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(value = "/rest/fixture")
public class FixtureController {

    @ResponseBody
    @RequestMapping(value = "/nextweek", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fixture> getNextWeek() {
        Fixture fixture = Fixture.builder()
                .fixture_id(1000)
                .awayTeam("awayTeam")
                .final_score("56")
                .homeTeam("homeTeam")
                .league_id(1)
                .round("3")
                .status("status")
                .build();
        return Collections.singletonList(fixture);
    }

    @ResponseBody
    @RequestMapping(value = "/lastweek", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fixture> getLastWeek() {
        Fixture fixture = Fixture.builder()
                .fixture_id(19990)
                .awayTeam("awayTeam9")
                .final_score("99")
                .homeTeam("homeTea9m")
                .league_id(15)
                .round("3444")
                .status("sttststtst")
                .build();
        return Collections.singletonList(fixture);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Fixture getNextWeek(@PathVariable Long id) {
        return Fixture.builder()
                .fixture_id(id.intValue())
                .awayTeam("awayTeam")
                .final_score("526")
                .homeTeam("homeTeam")
                .league_id(1)
                .round("32")
                .status("status")
                .build();
    }
}