package ru.kappers.logic.controller;

import com.google.gson.Gson;
import com.ibm.icu.text.Transliterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.kappers.model.Fixture;
import ru.kappers.model.catalog.Team;
import ru.kappers.model.dto.TeamBridgeDTO;
import ru.kappers.model.leonmodels.CompetitorLeon;
import ru.kappers.model.mapping.TeamBridge;
import ru.kappers.service.CompetitorLeonService;
import ru.kappers.service.FixtureService;
import ru.kappers.service.TeamBridgeService;
import ru.kappers.service.TeamService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Контроллер предназначен для маппинга сущностей Команд: Team и CompetitorLeon
 * @author Ashamaz Shomakhov
 */

@Slf4j
@RestController
@RequestMapping(value = "/rest/mapper/teams")
public class EntityMapperController {
    private final TeamService teamService;
    private final CompetitorLeonService competitorService;
    private static final String CYRILLIC_TO_LATIN = "Latin-Russian/BGN";
    private final FixtureService fService;
    private final TeamBridgeService teamBridgeService;
    private static final Gson GSON = new Gson();

    @Autowired
    public EntityMapperController(TeamService teamService, CompetitorLeonService competitorService, FixtureService fService, TeamBridgeService teamBridgeService) {
        this.teamService = teamService;
        this.competitorService = competitorService;
        this.fService = fService;
        this.teamBridgeService = teamBridgeService;
    }

    /**
     * getRapidTeams возвращает не смапленные сущности Team из базы данных
     */

    @ResponseBody
    @RequestMapping(value = "/rapidteams", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Team> getRapidTeams() {
        log.debug("getRapidTeams()...");
        List<Integer> mappedIds = teamBridgeService.getAll().stream().map(s -> s.getRapidTeam().getId()).collect(Collectors.toList());
        return teamService.getAll().stream().filter(s -> !mappedIds.contains(s.getId())).collect(Collectors.toList());
    }

    /**
     * getLeonCompetitors возвращает не смапленные сущности CompetitorLeon из базы данных
     */

    @ResponseBody
    @RequestMapping(value = "/competitors", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompetitorLeon> getLeonCompetitors() {
        log.debug("getLeonCompetitors()...");
        List<Long> mappedIds = teamBridgeService.getAll().stream().map(s -> s.getLeonCompetitor().getId()).collect(Collectors.toList());
        return competitorService.getAll().stream().filter(s -> !mappedIds.contains(s.getId())).collect(Collectors.toList());
    }

    /**
     * completeRapidTeamsFromExistFixtures получает все имеющиеся Fixture и сохраняет в сущности Team
     * все команды, участвующие в этих событиях
     */
    @ResponseBody
    @RequestMapping(value = "/getTeamsFromFixtures", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Team> completeRapidTeamsFromExistFixtures() {
        log.debug("completeRapidTeamsFromExistFixtures()...");
        Set<Team> teamsFromFixtures = new HashSet<>();
        List<Fixture> fixtureList = fService.getAll();
        for (Fixture f : fixtureList) {
            Team byId = teamService.getById(f.getAwayTeamId());
            if (byId == null) {
                teamsFromFixtures.add(teamService.save(Team.builder().id(f.getAwayTeamId()).name(f.getAwayTeam()).build()));
            }
            byId = teamService.getById(f.getHomeTeamId());
            if (byId == null) {
                teamsFromFixtures.add(teamService.save(Team.builder().id(f.getHomeTeamId()).name(f.getHomeTeam()).build()));
            }
        }
        return teamsFromFixtures;
    }

    /**
     * tryMappingRapidTeamAndLeonCompetitor - метод получает все сущности Team и CompetitorLeon
     * и пытается их смапить по названию через транслитерацию
     */
    @ResponseBody
    @RequestMapping(value = "/map", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Team, CompetitorLeon> tryMappingRapidTeamAndLeonCompetitor() {
        log.debug("tryMappingRapidTeamAndLeonCompetitor()...");
        Map<Team, CompetitorLeon> result = new HashMap<>();
        Set<String> rapidTeams = teamService.getAll().stream().map(Team::getName).collect(Collectors.toSet());
        log.debug("tryMappingRapidTeamAndLeonCompetitor(): получено " + rapidTeams.size() + " сущностей Team");
        List<CompetitorLeon> competitors = competitorService.getAll();
        List<String> leonComps = competitors.stream().map(CompetitorLeon::getName).collect(Collectors.toList());
        log.debug("tryMappingRapidTeamAndLeonCompetitor(): получено " + leonComps.size() + " сущностей CompetitorLeon");
        Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        for (String st : rapidTeams) {
            if (leonComps.contains(st)) {
                result.put(teamService.getByName(st), competitorService.getByName(st));
                saveTeamBridge(teamService.getByName(st), competitorService.getByName(st));
            } else {
                String res = toLatinTrans.transliterate(st);
                if (leonComps.contains(res)) {
                    result.put(teamService.getByName(st), competitorService.getByName(res));
                    saveTeamBridge(teamService.getByName(st), competitorService.getByName(res));
                }
            }
        }
        return result;
    }

    /**
     * saveMappedTeams - сохраняет в БД смапленную сущность, полученную через POST запрос с фронта
     * Пример JSON который надо получить
     * {
     * "rapidTeam":"1088",
     * "leonCompetitor":"281474976793815"
     * }
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TeamBridge saveMappedTeams(@RequestBody String content) {
        log.debug("saveMappedTeams(content: {})...", content);
        TeamBridgeDTO bridge = GSON.fromJson(content, TeamBridgeDTO.class);
        Team team = teamService.getById(Integer.parseInt(bridge.getRapidTeam()));
        CompetitorLeon competitorLeon = competitorService.getById(Long.parseLong(bridge.getLeonCompetitor()));
        return teamBridgeService.save(TeamBridge.builder().leonCompetitor(competitorLeon).rapidTeam(team).build());
    }

    private void saveTeamBridge(Team team, CompetitorLeon competitor) {
        TeamBridge bridge = TeamBridge.builder()
                .rapidTeam(team)
                .leonCompetitor(competitor)
                .build();
        teamBridgeService.save(bridge);
    }


}
