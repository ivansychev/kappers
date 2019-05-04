package ru.kappers.logic.odds;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import ru.kappers.model.dto.LeonOddsDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LeonBetParser implements BetParser<LeonOddsDTO> {
    private static final String LEON_ADDRESS = "https://www.leon.ru";

    @Override
    public List<String> loadUrlsOfEvents(String url) {
        List<String> urlsOfEvents = new ArrayList<>();

        URL address = null;
        try {
            address = new URL(LEON_ADDRESS + url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Не удалось сформировать URL", e);
        }
        StringBuilder sb = new StringBuilder();

        try (InputStreamReader is = new InputStreamReader(address.openConnection().getInputStream());
             BufferedReader br = new BufferedReader(is)) {
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
        } catch (IOException e) {
            log.error("Не удалось получить страницу. Возможно передан некорректный URL", e);
        }
        StringBuilder object = new StringBuilder();
        String obj = "";
        if (sb.toString().contains("initialEvents:")) {
            object.append(sb.toString().substring(sb.toString().indexOf("initialEvents:")));
            obj = object.substring(0, object.indexOf("</script>"))
                    .replace("initialEvents", "{\"initialEvents\"")
                    .substring(0, object.indexOf("refreshTimeout"))
                    .trim();
            obj = obj.substring(0, obj.length() - 1).concat("}");
        }
        log.info(obj);
        JsonElement element = new JsonParser().parse(obj);
        JsonArray arr = (JsonArray) ((JsonObject) element).get("initialEvents").getAsJsonObject().get("events");
        for (int i = 0; i < arr.size(); i++) {
            JsonElement el = arr.get(i);
            String eventLink = el.getAsJsonObject().get("url").getAsString();
            urlsOfEvents.add(eventLink);
        }
        return urlsOfEvents;
    }

    @Override
    public List<LeonOddsDTO> loadEventsOfTournament(String url) {
        return null;
    }
}
