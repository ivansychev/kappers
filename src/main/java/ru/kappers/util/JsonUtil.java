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
import ru.kappers.model.Fixture;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonUtil {
    public static JSONObject loadFixturesByLeague(int leagueId) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("https://api-football-v1.p.mashape.com/fixtures/league/" + leagueId)
                .header("X-Mashape-Key", "4UUu9YH9M1mshzEpnUwMzCwZ7Kr9p1zShpXjsndn50fifuusMu")
                .header("X-Mashape-Host", "api-football-v1.p.mashape.com")
                .asJson();
        JsonNode body = response.getBody();
        return body.getObject();
    }

    public static JSONObject loadFixturesByDate(LocalDate date) throws UnirestException {
        String formattedString = date.format(DateTimeFormatter.ISO_DATE);
        HttpResponse<JsonNode> response = Unirest.get("https://api-football-v1.p.mashape.com/fixtures/date/" + formattedString)
                .header("X-Mashape-Key", "4UUu9YH9M1mshzEpnUwMzCwZ7Kr9p1zShpXjsndn50fifuusMu")
                .header("X-Mashape-Host", "api-football-v1.p.mashape.com")
                .asJson();
        JsonNode body = response.getBody();
        return body.getObject();
    }

    public static JSONObject loadLiveFixtures() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("https://api-football-v1.p.mashape.com/fixtures/live")
                .header("X-Mashape-Key", "4UUu9YH9M1mshzEpnUwMzCwZ7Kr9p1zShpXjsndn50fifuusMu")
                .header("X-Mashape-Host", "api-football-v1.p.mashape.com")
                .asJson();
        JsonNode body = response.getBody();
        return body.getObject();
    }

    public static Map<Integer, Fixture> getFixturesFromJson(String object) {
        //TODO проставление id перенести сюда
        Gson gson = new Gson();
        Type itemsMapType = new TypeToken<Map<Integer, Fixture>>() {
        }.getType();
        JsonElement element = new JsonParser().parse(object);
        if (((JsonObject) element).get("body") != null) {
            element = ((JsonObject) element).get("body").getAsJsonObject();
        }
        JsonObject allResults = ((JsonObject) element).get("api").getAsJsonObject();
        JsonObject fixtures = (JsonObject) allResults.get("fixtures");
        Map<Integer, Fixture> elements;
        String replaceEmpties = fixtures.toString().replace("\"\"", "null");
     //   fixtures = gson.fromJson(replaceEmpties,JsonObject.class);
        elements = gson.fromJson(replaceEmpties, itemsMapType);
        return elements;
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
