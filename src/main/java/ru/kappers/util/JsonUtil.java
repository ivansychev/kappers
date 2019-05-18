package ru.kappers.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import ru.kappers.convert.FixtureDTOToFixtureConverter;
import ru.kappers.model.Fixture;
import ru.kappers.model.dto.rapidapi.FixtureRapidDTO;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

//todo Что то мне подсказывает что название класса не соответствует его содержимому. Можно было бы все эти методы просто перенести в FixtureService и его реализацию
public class JsonUtil {

    private static final Converter<FixtureRapidDTO, Fixture> fixtureDTOToFixtureConverter = new FixtureDTOToFixtureConverter();

    public static final Gson GSON = new Gson();
    public static final JsonParser JSON_PARSER = new JsonParser();

    public static JSONObject loadFixturesByLeague(int leagueId) throws UnirestException {
        return getObjectsFromRapidAPI("https://api-football-v1.p.mashape.com/fixtures/league/" + leagueId);
    }

    public static JSONObject loadFixturesByDate(LocalDate date) throws UnirestException {
        String formattedString = date.format(DateTimeFormatter.ISO_DATE);
        return getObjectsFromRapidAPI("https://api-football-v1.p.mashape.com/fixtures/date/" + formattedString);
    }

    public static JSONObject loadLiveFixtures() throws UnirestException {
        return getObjectsFromRapidAPI("https://api-football-v1.p.mashape.com/fixtures/live");
    }

    public static JSONObject loadTeamsByLeague(Integer leagueId) throws UnirestException {
        return getObjectsFromRapidAPI("https://api-football-v1.p.rapidapi.com/teams/league/" + leagueId);
    }

    public static JSONObject loadLeagues() throws UnirestException {
        return getObjectsFromRapidAPI("https://api-football-v1.p.rapidapi.com/leagues");
    }


    public static JSONObject loadLeaguesOfSeason(String year) throws UnirestException {
        return getObjectsFromRapidAPI("https://api-football-v1.p.rapidapi.com/leagues/season/"+year);
    }
    private static JSONObject getObjectsFromRapidAPI(String link) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get(link)
                .header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .header("X-RapidAPI-Key", "4UUu9YH9M1mshzEpnUwMzCwZ7Kr9p1zShpXjsndn50fifuusMu")
                .asJson();
        return response.getBody().getObject();
    }

    public static Map<Integer, Fixture> getFixturesFromJson(String object) {
        JsonObject jsonObject = (JsonObject) JSON_PARSER.parse(object);
        if (jsonObject.get("body") != null) {
            jsonObject = jsonObject.get("body").getAsJsonObject();
        }
        JsonObject allResults = jsonObject.get("api").getAsJsonObject();
        JsonObject fixtures = (JsonObject) allResults.get("fixtures");
        String replaceEmpties = fixtures.toString().replace("\"\"", "null");
        Map<Integer, FixtureRapidDTO> elements = GSON.fromJson(replaceEmpties, new TypeToken<Map<Integer, FixtureRapidDTO>>() {}.getType());
        return elements.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> fixtureDTOToFixtureConverter.convert(e.getValue())));
    }

    public static Map<Integer, Fixture> getFixturesFromFile(String filePath) {
        File file = new File(filePath);
        try (Reader r = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            return getFixturesFromJson(JSON_PARSER.parse(r).toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
