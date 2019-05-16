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
import ru.kappers.model.dto.rapidapi.FixtureRapidDTO;
import ru.kappers.model.dto.rapidapi.LeagueRapidDTO;
import ru.kappers.model.dto.rapidapi.TeamRapidDTO;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    private Converter<FixtureRapidDTO, Fixture> fixtureDTOToFixtureConverter;
    private Converter<EventDTO, Event> eventDTOEventConverter;
    private Converter<LeagueRapidDTO, League> leagueDTOLeagueConverter;
    private Converter<TeamRapidDTO, Team> teamRapidDTOTeamConverter;

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

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // регистрируем FixtureDTOToFixtureConverter в системе конвертаций Spring
        registry.addConverter(fixtureDTOToFixtureConverter);
        registry.addConverter(eventDTOEventConverter);
        registry.addConverter(leagueDTOLeagueConverter);
        registry.addConverter(teamRapidDTOTeamConverter);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/ui/view/index.html");
    }
}
