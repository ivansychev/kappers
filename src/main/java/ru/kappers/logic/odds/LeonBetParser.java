package ru.kappers.logic.odds;

import com.google.gson.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.kappers.exceptions.BetParserException;
import ru.kappers.model.dto.leon.OddsLeonDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Парсер сайта ООО "Леон"
 */
@Slf4j
public class LeonBetParser implements BetParser<OddsLeonDTO> {

    @Getter
    /** ссылка на сайт ООО "Леон" */
    private final String leonAddress; //TODO после заведения справочника, вынести в системный параметр
    private final Gson GSON = new Gson();
    private final JsonParser JSON_PARSER = new JsonParser();

    public LeonBetParser() {
        this("https://www.leon.ru");
    }

    /**
     * Создать парсер сайта ООО "Леон"
     *
     * @param leonAddress ссылка на сайт ООО "Леон"
     */
    public LeonBetParser(String leonAddress) {
        this.leonAddress = leonAddress;
    }

    @Override
    public List<String> loadEventUrlsOfTournament(String url) {
        log.debug("loadEventUrlsOfTournament(url: {})...", url);
        List<String> urlsOfEvents = new ArrayList<>();
        JsonArray arr = getArrayOfEventsByURL(url);
        for (int i = 0; i < arr.size(); i++) {
            JsonElement el = arr.get(i);
            String eventLink = el.getAsJsonObject().get("url").getAsString();
            urlsOfEvents.add(eventLink);
        }
        return urlsOfEvents;
    }

    @Override
    public List<OddsLeonDTO> getEventsWithOdds(List<String> urls) {
        log.debug("getEventsWithOdds(urls: {})...", urls);
        List<OddsLeonDTO> results = new ArrayList<>();
        for (String url : urls) {
            OddsLeonDTO oddsLeonDTO = loadEventOdds(url);
            if (oddsLeonDTO != null) {
                results.add(oddsLeonDTO);
                //TODO сохранение в БД
            }
        }
        return results;
    }

    /**
     * Метод возвращает {@link OddsLeonDTO} сущность, полученную из веб страницы конкретного евента
     *
     * @param url - линк веб страницы евента, который нужно распарсить
     */
    @Override
    public OddsLeonDTO loadEventOdds(String url) {
        log.info("loadEventOdds {}", url);
        JsonArray arr = getArrayOfEventsByURL(url);
        JsonObject object = arr.get(0).getAsJsonObject();
        Gson gson = new Gson();
        return gson.fromJson(object, OddsLeonDTO.class);
    }


    /**
     * Метод возвращает массив объектов JSON, полученных в результате парсинга веб страницы указанного урла
     *
     * @param url - линк веб страницы, которую нужно распарсить
     */
    private JsonArray getArrayOfEventsByURL(String url) {

        URL address = null;
        try {
            address = new URL(leonAddress + url);
        } catch (MalformedURLException e) {
            String msg = "Не удалось сформировать URL";
            log.error(msg, e);
            throw new BetParserException(msg, e);
        }
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                address.openConnection().getInputStream(), StandardCharsets.UTF_8))) {
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
        } catch (IOException e) {
            String msg = "Ошибка ввода/вывода во время загрузки данных со страницы " + url
                    + ". Возможно передан некорректный URL";
            log.error(msg, e);
            throw new BetParserException(msg, e);
        }
        StringBuilder object = new StringBuilder();
        String obj = "";
        String sbStr = sb.toString();
        int idx = sbStr.indexOf("initialEvents:");
        if (idx > -1) {
            object.append(sbStr.substring(idx));
            obj = object.substring(0, object.indexOf("</script>"))
                    .replace("initialEvents", "{\"initialEvents\"")
                    .substring(0, object.indexOf("refreshTimeout"))
                    .trim();
            obj = obj.substring(0, obj.length() - 1).concat("}");
        }
        log.debug("obj: {}", obj);
        JsonObject jsonObject = (JsonObject) JSON_PARSER.parse(obj);
        return (JsonArray) jsonObject.get("initialEvents")
                .getAsJsonObject()
                .get("events");
    }

}
