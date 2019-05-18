package ru.kappers.service.parser;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.kappers.model.catalog.League;
import ru.kappers.model.dto.rapidapi.LeagueRapidDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Rapid API парсер лиги
 */
@Slf4j
@Service
public class RapidAPILeagueParser extends BaseRapidAPIParser<League> {
    @Autowired
    public RapidAPILeagueParser(ConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public List<League> parseListFromJSON(String json) {
        log.debug("parseListFromJSON(json: {})...", json);
        JsonObject jsonObject = (JsonObject) jsonParser.parse(json);
        JsonObject allResults = jsonObject.get("api").getAsJsonObject();
        JsonObject array = (JsonObject) allResults.get("leagues");
        Map<Integer, LeagueRapidDTO> elements = gson.fromJson(array, new TypeToken<Map<Integer, LeagueRapidDTO>>() {}.getType());
        return elements.entrySet().stream()
                .map(entry -> conversionService.convert(entry.getValue(), League.class))
                .collect(Collectors.toList());
    }
}
