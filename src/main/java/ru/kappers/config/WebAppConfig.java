package ru.kappers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.kappers.model.Event;
import ru.kappers.model.Fixture;
import ru.kappers.model.catalog.League;
import ru.kappers.model.catalog.Team;
import ru.kappers.model.dto.EventDTO;
import ru.kappers.model.dto.leon.*;
import ru.kappers.model.dto.rapidapi.FixtureRapidDTO;
import ru.kappers.model.dto.rapidapi.LeagueRapidDTO;
import ru.kappers.model.dto.rapidapi.TeamRapidDTO;
import ru.kappers.model.leonmodels.*;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    private Converter<FixtureRapidDTO, Fixture> fixtureDTOToFixtureConverter;
    private Converter<EventDTO, Event> eventDTOEventConverter;
    private Converter<LeagueRapidDTO, League> leagueDTOLeagueConverter;
    private Converter<TeamRapidDTO, Team> teamRapidDTOTeamConverter;
    private Converter<CompetitorLeonDTO, CompetitorLeon> competitorLeonDTOCompetitorLeonConverter;
    private Converter<LeagueLeonDTO, LeagueLeon> leagueLeonDTOLeagueLeonConverter;
    private Converter<MarketLeonDTO, MarketLeon> marketLeonDTOMarketLeonConverter;
    private Converter<OddsLeonDTO, OddsLeon> oddsLeonDTOOddsLeonConverter;
    private Converter<RunnerLeonDTO, RunnerLeon> runnerLeonDTORunnerLeonConverter;
    private Converter<SportLeonDTO, SportLeon> sportLeonDTOSportLeonConverter;

    @Autowired
    public void setFixtureDTOToFixtureConverter(Converter<FixtureRapidDTO, Fixture> fixtureDTOToFixtureConverter) {
        this.fixtureDTOToFixtureConverter = fixtureDTOToFixtureConverter;
    }

    @Autowired
    public void setEventDTOEventConverter(Converter<EventDTO, Event> eventDTOEventConverter) {
        this.eventDTOEventConverter = eventDTOEventConverter;
    }

    @Autowired
    public void setLeagueDTOLeagueConverter(Converter<LeagueRapidDTO, League> leagueDTOLeagueConverter) {
        this.leagueDTOLeagueConverter = leagueDTOLeagueConverter;
    }

    @Autowired
    public void setTeamRapidDTOTeamConverter(Converter<TeamRapidDTO, Team> teamRapidDTOTeamConverter) {
        this.teamRapidDTOTeamConverter = teamRapidDTOTeamConverter;
    }

    @Autowired
    public void setCompetitorLeonDTOCompetitorLeonConverter(Converter<CompetitorLeonDTO, CompetitorLeon> competitorLeonDTOCompetitorLeonConverter) {
        this.competitorLeonDTOCompetitorLeonConverter = competitorLeonDTOCompetitorLeonConverter;
    }

    @Autowired
    public void setLeagueLeonDTOLeagueLeonConverter(Converter<LeagueLeonDTO, LeagueLeon> leagueLeonDTOLeagueLeonConverter) {
        this.leagueLeonDTOLeagueLeonConverter = leagueLeonDTOLeagueLeonConverter;
    }

    @Autowired
    public void setMarketLeonDTOMarketLeonConverter(Converter<MarketLeonDTO, MarketLeon> marketLeonDTOMarketLeonConverter) {
        this.marketLeonDTOMarketLeonConverter = marketLeonDTOMarketLeonConverter;
    }

    @Autowired
    public void setOddsLeonDTOOddsLeonConverter(Converter<OddsLeonDTO, OddsLeon> oddsLeonDTOOddsLeonConverter) {
        this.oddsLeonDTOOddsLeonConverter = oddsLeonDTOOddsLeonConverter;
    }

    @Autowired
    public void setRunnerLeonDTORunnerLeonConverter(Converter<RunnerLeonDTO, RunnerLeon> runnerLeonDTORunnerLeonConverter) {
        this.runnerLeonDTORunnerLeonConverter = runnerLeonDTORunnerLeonConverter;
    }

    @Autowired
    public void setSportLeonDTOSportLeonConverter(Converter<SportLeonDTO, SportLeon> sportLeonDTOSportLeonConverter) {
        this.sportLeonDTOSportLeonConverter = sportLeonDTOSportLeonConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // регистрируем FixtureDTOToFixtureConverter в системе конвертаций Spring
        registry.addConverter(fixtureDTOToFixtureConverter);
        registry.addConverter(eventDTOEventConverter);
        registry.addConverter(leagueDTOLeagueConverter);
        registry.addConverter(teamRapidDTOTeamConverter);
        registry.addConverter(competitorLeonDTOCompetitorLeonConverter);
        registry.addConverter(leagueLeonDTOLeagueLeonConverter);
        registry.addConverter(marketLeonDTOMarketLeonConverter);
        registry.addConverter(oddsLeonDTOOddsLeonConverter);
        registry.addConverter(runnerLeonDTORunnerLeonConverter);
        registry.addConverter(sportLeonDTOSportLeonConverter);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/ui/view/index.html");
    }
}
