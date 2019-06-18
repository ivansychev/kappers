package ru.kappers.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.kappers.model.Fixture;
import ru.kappers.model.dto.rapidapi.FixtureRapidDTO;
import ru.kappers.service.JsonService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JsonServiceImpl implements JsonService {

    private final ConversionService conversionService;

    public final Gson GSON = new Gson();
    public final JsonParser JSON_PARSER = new JsonParser();


    @Autowired
    public JsonServiceImpl(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public JSONObject loadFixturesByLeague(int leagueId) throws UnirestException {
        return getObjectsFromRapidAPI("https://api-football-v1.p.mashape.com/fixtures/league/" + leagueId);
    }

    @Override
    public JSONObject loadFixturesByDate(LocalDate date) throws UnirestException {
        String formattedString = date.format(DateTimeFormatter.ISO_DATE);
        return getObjectsFromRapidAPI("https://api-football-v1.p.mashape.com/fixtures/date/" + formattedString);
    }

    @Override
    public JSONObject loadLiveFixtures() throws UnirestException {
        return getObjectsFromRapidAPI("https://api-football-v1.p.mashape.com/fixtures/live");
    }

    @Override
    public JSONObject loadTeamsByLeague(Integer leagueId) throws UnirestException {
        return getObjectsFromRapidAPI("https://api-football-v1.p.rapidapi.com/teams/league/" + leagueId);
    }

    @Override
    public JSONObject loadLeagues() throws UnirestException {
        return getObjectsFromRapidAPI("https://api-football-v1.p.rapidapi.com/leagues");
    }

    @Override
    public JSONObject loadLeaguesOfSeason(String year) throws UnirestException {
        return getObjectsFromRapidAPI("https://api-football-v1.p.rapidapi.com/leagues/season/"+year);
    }

    protected JSONObject getObjectsFromRapidAPI(String link) throws UnirestException {
        log.debug("getObjectsFromRapidAPI(link: {})", link);
        HttpResponse<JsonNode> response = Unirest.get(link)
                .header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .header("X-RapidAPI-Key", "4UUu9YH9M1mshzEpnUwMzCwZ7Kr9p1zShpXjsndn50fifuusMu")
                .asJson();
        return response.getBody().getObject();
    }

    @Override
    public Map<Integer, Fixture> getFixturesFromJson(String object) {
        log.debug("getFixturesFromJson(object: {})", object);
        JsonObject jsonObject = (JsonObject) JSON_PARSER.parse(object);
        if (jsonObject.get("body") != null) {
            jsonObject = jsonObject.get("body").getAsJsonObject();
        }
        JsonObject allResults = jsonObject.get("api").getAsJsonObject();
        JsonObject fixtures = (JsonObject) allResults.get("fixtures");
        String replaceEmpties = fixtures.toString().replace("\"\"", "null");
        Map<Integer, FixtureRapidDTO> elements = GSON.fromJson(replaceEmpties, new TypeToken<Map<Integer, FixtureRapidDTO>>() {}.getType());
        return elements.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> conversionService.convert(e.getValue(), Fixture.class)));
    }

    @Override
    public Map<Integer, Fixture> getFixturesFromFile(String filePath) {
        log.debug("getFixturesFromFile(filePath: {})", filePath);
        File file = new File(filePath);
        try (Reader r = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            return getFixturesFromJson(JSON_PARSER.parse(r).toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
