package ru.kappers.service.parser;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.kappers.model.catalog.Team;
import ru.kappers.model.dto.rapidapi.TeamRapidDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Rapid API парсер команды
 */
@Slf4j
@Service
public class RapidAPITeamParser extends BaseRapidAPIParser<Team> {
    @Autowired
    public RapidAPITeamParser(ConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public List<Team> parseListFromJSON(String json) {
        log.debug("parseListFromJSON(json: {})...", json);
        JsonObject jsonObject = (JsonObject) jsonParser.parse(json);
        JsonObject allResults = jsonObject.get("api").getAsJsonObject();
        JsonObject array = (JsonObject) allResults.get("teams");
        Map<Integer, TeamRapidDTO> elements = gson.fromJson(array, new TypeToken<Map<Integer, TeamRapidDTO>>() {
        }.getType());
        return elements.entrySet().stream()
                .map(entry -> conversionService.convert(entry.getValue(), Team.class))
                .collect(Collectors.toList());
    }
}
