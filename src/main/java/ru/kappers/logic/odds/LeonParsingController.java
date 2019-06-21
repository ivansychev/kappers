package ru.kappers.logic.odds;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kappers.convert.OddsLeonDTOToOddsLeonConverter;
import ru.kappers.model.dto.leon.MarketLeonDTO;
import ru.kappers.model.dto.leon.OddsLeonDTO;
import ru.kappers.model.dto.leon.RunnerLeonDTO;
import ru.kappers.model.leonmodels.MarketLeon;
import ru.kappers.model.leonmodels.OddsLeon;
import ru.kappers.model.leonmodels.RunnerLeon;
import ru.kappers.service.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/rest/leon")
public class LeonParsingController {
    private static final Gson GSON = new Gson();

    private final CompetitorLeonService competitorService;
    private final LeagueLeonService leagueService;
    private final OddsLeonService oddsLeonService;
    private final MarketLeonService marketService;

    @Autowired
    public LeonParsingController(CompetitorLeonService competitorService, LeagueLeonService leagueService, OddsLeonService oddsLeonService, MarketLeonService marketService) {
        this.competitorService = competitorService;
        this.leagueService = leagueService;
        this.oddsLeonService = oddsLeonService;
        this.marketService = marketService;
    }

    /**
     * Пост запрос с телом
     * {
     * "url":"/events/Soccer/281474976710874-Americas-Copa-America"
     * }
     * где url это путь к турниру, события из которой хотим сохранить в нашу базу
     */

    @RequestMapping(value = "/oddLeons", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOddLeons(@RequestBody String content) {
        JsonObject jObject = GSON.fromJson(content, JsonElement.class).getAsJsonObject();
        String url = jObject.get("url").getAsString();
        BetParser<OddsLeonDTO> parser = new LeonBetParser();
        List<String> list = parser.loadEventUrlsOfTournament(url);
        List<OddsLeonDTO> eventsWithOdds = parser.getEventsWithOdds(list);
        Converter<OddsLeonDTO, OddsLeon> converter = new OddsLeonDTOToOddsLeonConverter(competitorService, leagueService);
        for (OddsLeonDTO dto : eventsWithOdds) {
            OddsLeon odd = converter.convert(dto);
            List<RunnerLeon> runners = runnerLeonConverter(dto.getMarkets());
            if (odd != null) {
                runners.forEach(s -> s.setOdd(odd));
                odd.setRunners(runners);
                oddsLeonService.save(odd);
            }
        }
        return new ResponseEntity<>(
                "Odds from Leon are saved ",
                HttpStatus.OK);
    }

    /**
     * Из MarketLeonDTO, который мы получили с сайта Леон, вытаскиваем всех раннеров. Сами маркеты сохраняем отдельной сущностью, они могут повторяться
     */
    private List<RunnerLeon> runnerLeonConverter(List<MarketLeonDTO> markets) {
        List<RunnerLeon> runners = new ArrayList<>();
        for (MarketLeonDTO market : markets) {
            MarketLeon m = marketService.getByName(market.getName());
            if (m == null) {
                m = marketService.save(MarketLeon.builder()
                        .id(market.getId())
                        .name(market.getName())
                        .open(market.isOpen())
                        .build());
            }
            runners.addAll(getRunnersFromMarketDTO(market, m));
        }
        return runners;
    }
/**
 * Вытаскиваются из маркета все раннеры и конвертируются в нашу сущность RunnerLeon.
 * Внимание! В этом методе не происходит сохранение. Сохранение происходит вместе с сущзностью OddsLeon каскадом
 * */
    private List<RunnerLeon> getRunnersFromMarketDTO(MarketLeonDTO market, MarketLeon m) {
        List<RunnerLeon> runners = new ArrayList<>();
        List<RunnerLeonDTO> dtos = market.getRunners();
        for (RunnerLeonDTO dto : dtos) {
            RunnerLeon runner = RunnerLeon.builder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .price(dto.getPrice())
                    .open(dto.isOpen())
                    .market(m)
                    .tags(dto.getTags().toString())
                    .build();
            runners.add(runner);
        }
        return runners;
    }
}
