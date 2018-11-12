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

import java.lang.reflect.Type;
import java.util.Map;

public class JsonUtil {
    public static JSONObject loadFixturesByLeague(int leagueId) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("https://api-football-v1.p.mashape.com/fixtures/league/"+leagueId)
                .header("X-Mashape-Key", "4UUu9YH9M1mshzEpnUwMzCwZ7Kr9p1zShpXjsndn50fifuusMu")
                .header("X-Mashape-Host", "api-football-v1.p.mashape.com")
                .asJson();
        JsonNode body = response.getBody();
        return body.getObject();
    }

    public static Map<Integer, Fixture> getFixturesFromJson(String object){
        Type itemsMapType = new TypeToken<Map<Integer, Fixture>>() {
        }.getType();
        JsonElement element =  new JsonParser().parse(object);
        JsonObject allResults = ((JsonObject) element).get("api").getAsJsonObject();
        JsonObject fixtures = (JsonObject) allResults.get("fixtures");
        Map<Integer, Fixture> elements = new Gson().fromJson(fixtures, itemsMapType);
        return elements;
    }
}
