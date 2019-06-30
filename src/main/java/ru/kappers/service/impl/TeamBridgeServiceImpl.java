package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.catalog.Team;
import ru.kappers.model.leonmodels.CompetitorLeon;
import ru.kappers.model.mapping.TeamBridge;
import ru.kappers.repository.TeamBridgeRepository;
import ru.kappers.service.CompetitorLeonService;
import ru.kappers.service.TeamBridgeService;
import ru.kappers.service.TeamService;

import java.util.List;

/**
 * Сервис TeamBridge
 *
 * @author Ashamaz Shomakhov
 */
@Service
@Slf4j
public class TeamBridgeServiceImpl implements TeamBridgeService {

    private final TeamBridgeRepository repository;
    private final CompetitorLeonService competitorService;
    private final TeamService teamService;

    @Autowired
    public TeamBridgeServiceImpl(TeamBridgeRepository repository, CompetitorLeonService competitorService, TeamService teamService) {
        this.repository = repository;
        this.competitorService = competitorService;
        this.teamService = teamService;
    }

    @Override
    public TeamBridge save(TeamBridge bridge) {
        log.debug("save(bridge: {})...", bridge);
        TeamBridge byLeonCompetitor = repository.getByLeonCompetitor(bridge.getLeonCompetitor());
        TeamBridge byRapidTeam = repository.getByRapidTeam(bridge.getRapidTeam());
        if (byLeonCompetitor == null && byRapidTeam == null)
            return repository.save(bridge);
        else {
            log.error("Не удалось сохранить маппинг. Возможно у одной или обоих команд уже есть сопоставленная сущность");
            return byLeonCompetitor != null ? byLeonCompetitor : byRapidTeam;
        }

    }

    @Override
    public TeamBridge get(TeamBridge bridge) {
        log.debug("get(bridge: {})...", bridge);
        return getById(bridge.getId());
    }

    @Override
    public TeamBridge getById(Integer bridgeId) {
        log.debug("getById(bridgeId: {})...", bridgeId);
        return repository.findById(bridgeId).orElse(null);
    }

    @Override
    public void delete(TeamBridge bridge) {
        log.debug("delete(bridge: {})...", bridge);
        repository.delete(bridge);
    }

    @Override
    public TeamBridge update(TeamBridge bridge) {
        log.debug("update(bridge: {})...", bridge);
        TeamBridge byId = null;
        if (bridge.getId() != null) {
            byId = getById(bridge.getId());
        }
        if (byId == null)
            return save(bridge);
        else {
            byId.setLeonCompetitor(bridge.getLeonCompetitor());
            byId.setRapidTeam(bridge.getRapidTeam());
            return save(byId);
        }
    }

    @Override
    public List<TeamBridge> getAll() {
        log.debug("getAll(TeamBridge)");
        return repository.findAll();
    }

    @Override
    public TeamBridge getByRapidTeam(Team team) {
        log.debug("getByRapidTeam(team: {})...", team);
        return repository.getByRapidTeam(team);
    }

    @Override
    public TeamBridge getByCompetitorLeon(CompetitorLeon competitor) {
        log.debug("getByCompetitorLeon(competitor: {})...", competitor);
        return repository.getByLeonCompetitor(competitor);
    }

    @Override
    public CompetitorLeon getCompetitorByTeam(Team team) {
        log.debug("getCompetitorByTeam(team: {})...", team);
        TeamBridge byRapidTeam = getByRapidTeam(team);
        if (byRapidTeam != null)
            return competitorService.getById(byRapidTeam.getLeonCompetitor().getId());
        return null;
    }

    @Override
    public Team getTeamByCompetitorLeon(CompetitorLeon competitor) {
        log.debug("getTeamByCompetitorLeon(competitor: {})...", competitor);
        TeamBridge byCompetitor = getByCompetitorLeon(competitor);
        if (byCompetitor != null)
            return teamService.getById(byCompetitor.getRapidTeam().getId());
        return null;
    }
}
