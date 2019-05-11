package ru.kappers.logic.odds;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import ru.kappers.exceptions.BetParserException;
import ru.kappers.model.dto.leon.OddsLeonDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LeonBetParser implements BetParser<OddsLeonDTO> {

    private static final String LEON_ADDRESS = "https://www.leon.ru"; //TODO после заведения справочника, вынести в системный параметр

    /**
     * Метод возвращает список урлов доступных спортивных событий конкретного турнира
     *
     * @param url - линк веб страницы турнира, из котогрого нужно получить список событий
     */
    @Override
    public List<String> loadEventUrlsOfTournament(String url) {
        log.info("loadEventUrlsOfTournament {}", url);
        List<String> urlsOfEvents = new ArrayList<>();
        JsonArray arr = getArrayOfEventsByURL(url);
        for (int i = 0; i < arr.size(); i++) {
            JsonElement el = arr.get(i);
            String eventLink = el.getAsJsonObject().get("url").getAsString();
            urlsOfEvents.add(eventLink);
        }
        return urlsOfEvents;
    }

    /**
     * Метод возвращает список событий конкретного турнира
     *
     * @param urls - список линков, по которым нужно итерироваться и получать спортивные события
     */
    @Override
    public List<OddsLeonDTO> getEventsWithOdds(List<String> urls) {
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
            address = new URL(LEON_ADDRESS + url);
        } catch (MalformedURLException e) {
            log.error("Не удалось сформировать URL", e);
            throw new BetParserException("Не удалось сформировать URL", e);
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
            throw new BetParserException("Ошибка ввода/вывода во время загрузки данных со страницы " + url, e);
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
        return (JsonArray) ((JsonObject) element).get("initialEvents").getAsJsonObject().get("events");
    }

}
