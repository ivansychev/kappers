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
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

//todo Что то мне подсказывает что название класса не соответствует его содержимому. Можно было бы все эти методы просто перенести в FixtureService и его реализацию
public class JsonUtil {

    private static final Converter<FixtureRapidDTO, Fixture> fixtureDTOToFixtureConverter = new FixtureDTOToFixtureConverter();

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
        JsonNode body = response.getBody();
        return body.getObject();
    }

    public static Map<Integer, Fixture> getFixturesFromJson(String object) {
        Gson gson = new Gson();
        Type itemsMapType = new TypeToken<Map<Integer, FixtureRapidDTO>>() {
        }.getType();
        JsonElement element = new JsonParser().parse(object);
        if (((JsonObject) element).get("body") != null) {
            element = ((JsonObject) element).get("body").getAsJsonObject();
        }
        JsonObject allResults = ((JsonObject) element).get("api").getAsJsonObject();
        JsonObject fixtures = (JsonObject) allResults.get("fixtures");
        Map<Integer, FixtureRapidDTO> elements;
        String replaceEmpties = fixtures.toString().replace("\"\"", "null");
        //   fixtures = gson.fromJson(replaceEmpties,JsonObject.class);
        elements = gson.fromJson(replaceEmpties, itemsMapType);
        Map<Integer, Fixture> result = new HashMap<>();
        for (Map.Entry<Integer, FixtureRapidDTO> record : elements.entrySet()) {
            result.put(record.getKey(), fixtureDTOToFixtureConverter.convert(record.getValue()));
        }
        return result;
    }

    public static Map<Integer, Fixture> getFixturesFromFile(String filePath) {
        File file = new File(filePath);
        JsonElement element;
        try (InputStream is = new FileInputStream(file)) {
            Reader r = new InputStreamReader(is, StandardCharsets.UTF_8);
            element = new JsonParser().parse(r);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getFixturesFromJson(element.toString());
    }


}
