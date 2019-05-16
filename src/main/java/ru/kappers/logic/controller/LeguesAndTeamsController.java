package ru.kappers.logic.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.kappers.exceptions.UnirestAPIException;
import ru.kappers.model.catalog.League;
import ru.kappers.model.catalog.Team;
import ru.kappers.model.dto.rapidapi.LeagueRapidDTO;
import ru.kappers.model.dto.rapidapi.TeamRapidDTO;
import ru.kappers.service.LeagueService;
import ru.kappers.service.TeamService;
import ru.kappers.util.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/rest/api/v1")
public class LeguesAndTeamsController {
    @Autowired
    private Converter<LeagueRapidDTO, League> leagueConverter;

    @Autowired
    private Converter<TeamRapidDTO, Team> teamConverter;

    @Autowired
    private LeagueService leagueService;
    @Autowired
    private TeamService teamService;

    private Gson gson = new Gson();

    @ResponseBody
    @RequestMapping(value = "/league", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<League> getLeaguesList() {
        log.debug("getLeaguesList{}");
        Type itemsMapType = new TypeToken<Map<Integer, LeagueRapidDTO>>() {
        }.getType();
        List<League> list = new ArrayList<>();
        try {
            JSONObject jsonObject = JsonUtil.loadLeagues();
            JsonElement element = new JsonParser().parse(jsonObject.toString());
            JsonObject allResults = ((JsonObject) element).get("api").getAsJsonObject();
            JsonObject array = (JsonObject) allResults.get("leagues");
            Map<Integer, LeagueRapidDTO> elements = gson.fromJson(array, itemsMapType);
            for (Map.Entry<Integer, LeagueRapidDTO> entry : elements.entrySet()) {
                League league = leagueConverter.convert(entry.getValue());
                league = leagueService.save(league);
                list.add(league);
            }

        } catch (UnirestException e) {
            throw new UnirestAPIException("Ошибка получения списка лиг по Rapid API", e);
        }
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "/leagues/season/{season}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<League> getLeaguesListBySeason(@PathVariable String season) {
        log.debug("getLeaguesListBySeason{}", season);

        Type itemsMapType = new TypeToken<Map<Integer, LeagueRapidDTO>>() {
        }.getType();
        List<League> list = new ArrayList<>();
        try {
            JSONObject jsonObject = JsonUtil.loadLeaguesOfSeason(season);
            JsonElement element = new JsonParser().parse(jsonObject.toString());
            JsonObject allResults = ((JsonObject) element).get("api").getAsJsonObject();
            JsonObject array = (JsonObject) allResults.get("leagues");
            Map<Integer, LeagueRapidDTO> elements = gson.fromJson(array, itemsMapType);
            for (Map.Entry<Integer, LeagueRapidDTO> entry : elements.entrySet()) {
                League league = leagueConverter.convert(entry.getValue());
                league = leagueService.save(league);
                list.add(league);
            }
        } catch (UnirestException e) {
            throw new UnirestAPIException("Ошибка получения списка лиг по Rapid API за "+season+" сезон", e);
        }
        return list;
        //TODO в выдавемом JSON часовой пояс на UTC, а надо на UTC+3
    }

    @ResponseBody
    @RequestMapping(value = "/teams/league/{leagueId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Team> getTeamsOfLeague(@PathVariable Integer leagueId) {
        log.debug("getTeamsOfLeague{}", leagueId);

        Type itemsMapType = new TypeToken<Map<Integer, TeamRapidDTO>>() {
        }.getType();
        List<Team> list = new ArrayList<>();
        try {
            JSONObject jsonObject = JsonUtil.loadTeamsByLeague(leagueId);
            JsonElement element = new JsonParser().parse(jsonObject.toString());
            JsonObject allResults = ((JsonObject) element).get("api").getAsJsonObject();
            JsonObject array = (JsonObject) allResults.get("teams");
            Map<Integer, TeamRapidDTO> elements = gson.fromJson(array, itemsMapType);
            for (Map.Entry<Integer, TeamRapidDTO> entry : elements.entrySet()) {
                Team team = teamConverter.convert(entry.getValue());
                team = teamService.save(team);
                list.add(team);
            }
        } catch (UnirestException e) {
            throw new UnirestAPIException("Ошибка получения списка команд лиги "+leagueId, e);
        }
        return list;
    }
}
