package ru.kappers.logic.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.kappers.model.dto.rapidapi.FixtureRapidDTO;
import ru.kappers.model.dto.rapidapi.LeagueRapidDTO;
import ru.kappers.model.dto.rapidapi.TeamRapidDTO;
import ru.kappers.util.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/rest/api/v1")
public class LeguesAndTeamsController {
    private Gson gson = new Gson();
//TODO возвращаемые типы переделать в наши ентити, когда заведу и сделаю сохранение
    @ResponseBody
    @RequestMapping(value = "/league", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LeagueRapidDTO> getLeaguesList() {
        log.debug("getLeaguesList{}");
        Type itemsMapType = new TypeToken<Map<Integer, LeagueRapidDTO>>() {
        }.getType();
        List<LeagueRapidDTO> list = new ArrayList<>();
        try {
            JSONObject jsonObject = JsonUtil.loadLeagues();
            JsonElement element = new JsonParser().parse(jsonObject.toString());
            JsonObject allResults = ((JsonObject) element).get("api").getAsJsonObject();
            JsonObject array = (JsonObject) allResults.get("leagues");
            Map<Integer, LeagueRapidDTO> elements = gson.fromJson(array, itemsMapType);
            for (Map.Entry<Integer, LeagueRapidDTO> entry : elements.entrySet()) {
                list.add(entry.getValue());
            }

        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "/leagues/season/{season}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LeagueRapidDTO> getLeaguesListBySeason(@PathVariable String season) {
        log.debug("getLeaguesListBySeason{}", season);

        Type itemsMapType = new TypeToken<Map<Integer, LeagueRapidDTO>>() {
        }.getType();
        List<LeagueRapidDTO> list = new ArrayList<>();
        try {
            JSONObject jsonObject = JsonUtil.loadLeaguesOfSeason(season);
            JsonElement element = new JsonParser().parse(jsonObject.toString());
            JsonObject allResults = ((JsonObject) element).get("api").getAsJsonObject();
            JsonObject array = (JsonObject) allResults.get("leagues");
            Map<Integer, LeagueRapidDTO> elements = gson.fromJson(array, itemsMapType);
            for (Map.Entry<Integer, LeagueRapidDTO> entry : elements.entrySet()) {
                list.add(entry.getValue());
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "/teams/league/{leagueId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TeamRapidDTO> getTeamsOfLeague(@PathVariable Integer leagueId) {
        log.debug("getTeamsOfLeague{}", leagueId);

        Type itemsMapType = new TypeToken<Map<Integer, TeamRapidDTO>>() {
        }.getType();
        List<TeamRapidDTO> list = new ArrayList<>();
        try {
            JSONObject jsonObject = JsonUtil.loadTeamsByLeague(leagueId);
            JsonElement element = new JsonParser().parse(jsonObject.toString());
            JsonObject allResults = ((JsonObject) element).get("api").getAsJsonObject();
            JsonObject array = (JsonObject) allResults.get("teams");
            Map<Integer, TeamRapidDTO> elements = gson.fromJson(array, itemsMapType);
            for (Map.Entry<Integer, TeamRapidDTO> entry : elements.entrySet()) {
                list.add(entry.getValue());
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
