package ru.kappers.logic.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kappers.exceptions.UnirestAPIException;
import ru.kappers.model.catalog.League;
import ru.kappers.model.catalog.Team;
import ru.kappers.service.JsonService;
import ru.kappers.service.LeagueService;
import ru.kappers.service.MessageTranslator;
import ru.kappers.service.TeamService;
import ru.kappers.service.parser.RapidAPIParser;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LeguesAndTeamsControllerTest {

//    @InjectMocks
    private LeguesAndTeamsController controller;
    @Mock
    private LeagueService leagueService;
    @Mock
    private TeamService teamService;
    @Mock
    private RapidAPIParser<League> leagueRapidAPIParser;
    @Mock
    private RapidAPIParser<Team> teamRapidAPIParser;
    @Mock
    private JsonService jsonService;
    @Mock
    private MessageTranslator messageTranslator;

    @Before
    public void setUp() throws Exception {
        // что то InjectMocks в данном случае работает только в отладке, приходится вручную создавать экземпляр
        controller = new LeguesAndTeamsController(leagueService, teamService, leagueRapidAPIParser, teamRapidAPIParser,
                jsonService, messageTranslator);
    }

    @Test
    public void getLeaguesList() throws Exception {
        final JSONObject jsonObject = mock(JSONObject.class);
        final String jsonStr = "testJson";
        final List<League> leagueList = Arrays.asList(mock(League.class), mock(League.class));
        when(jsonObject.toString()).thenReturn(jsonStr);
        when(jsonService.loadLeagues()).thenReturn(jsonObject);
        when(leagueRapidAPIParser.parseListFromJSON(jsonStr)).thenReturn(leagueList);
        when(leagueService.save(any(League.class))).then(invocation -> invocation.getArgument(0));

        final List<League> resultList = controller.getLeaguesList();

        assertThat(resultList, is(notNullValue()));
        assertThat(resultList, is(leagueList));
        verify(jsonService).loadLeagues();
        verify(leagueRapidAPIParser).parseListFromJSON(jsonStr);
        verify(leagueService, times(leagueList.size())).save(any(League.class));
    }

    @Test(expected = UnirestAPIException.class)
    public void getLeaguesListMustThrowExceptionIfJsonServiceThrow() throws Exception {
        when(jsonService.loadLeagues()).thenThrow(UnirestException.class);

        controller.getLeaguesList();
    }

    @Test
    public void getLeaguesListBySeason() throws Exception {
        final String season = "2019";
        final JSONObject jsonObject = mock(JSONObject.class);
        final String jsonStr = "testJson";
        final List<League> leagueList = Arrays.asList(mock(League.class), mock(League.class));
        when(jsonObject.toString()).thenReturn(jsonStr);
        when(jsonService.loadLeaguesOfSeason(season)).thenReturn(jsonObject);
        when(leagueRapidAPIParser.parseListFromJSON(jsonStr)).thenReturn(leagueList);
        when(leagueService.save(any(League.class))).then(invocation -> invocation.getArgument(0));

        final List<League> resultList = controller.getLeaguesListBySeason(season);

        assertThat(resultList, is(notNullValue()));
        verify(jsonService).loadLeaguesOfSeason(season);
        verify(leagueRapidAPIParser).parseListFromJSON(jsonStr);
        verify(leagueService, times(leagueList.size())).save(any(League.class));
        assertThat(resultList, is(leagueList));
    }

    @Test(expected = UnirestAPIException.class)
    public void getLeaguesListBySeasonMustThrowExceptionIfJsonServiceThrow() throws Exception {
        when(jsonService.loadLeaguesOfSeason(anyString())).thenThrow(UnirestException.class);

        controller.getLeaguesListBySeason("2019");
    }

    @Test
    public void getTeamsOfLeague() throws Exception {
        final Integer leagueId = 1;
        final JSONObject jsonObject = mock(JSONObject.class);
        final String jsonStr = "testJson";
        final List<Team> teamList = Arrays.asList(mock(Team.class), mock(Team.class));
        when(jsonObject.toString()).thenReturn(jsonStr);
        when(jsonService.loadTeamsByLeague(leagueId)).thenReturn(jsonObject);
        when(teamRapidAPIParser.parseListFromJSON(jsonStr)).thenReturn(teamList);
        when(teamService.save(any(Team.class))).then(invocation -> invocation.getArgument(0));

        final List<Team> resultList = controller.getTeamsOfLeague(leagueId);

        assertThat(resultList, is(notNullValue()));
        verify(jsonService).loadTeamsByLeague(leagueId);
        verify(teamRapidAPIParser).parseListFromJSON(jsonStr);
        verify(teamService, times(teamList.size())).save(any(Team.class));
        assertThat(resultList, is(teamList));
    }

    @Test(expected = UnirestAPIException.class)
    public void getTeamsOfLeagueMustThrowExceptionIfJsonServiceThrow() throws Exception {
        when(jsonService.loadTeamsByLeague(anyInt())).thenThrow(UnirestException.class);

        controller.getTeamsOfLeague(1);
    }

    @Test
    public void saveEntities() {
        final List<League> leagueList = Arrays.asList(mock(League.class), mock(League.class));
        when(leagueService.save(any(League.class))).then(invocation -> invocation.getArgument(0));

        final List<League> resultList = controller.saveEntities(leagueList, leagueService::save);

        assertThat(resultList, is(notNullValue()));
        assertThat(resultList, is(leagueList));
        verify(leagueService, times(leagueList.size())).save(any(League.class));
    }
}