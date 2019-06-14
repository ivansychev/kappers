package ru.kappers.service.impl;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JsonServiceImplTest {
    @InjectMocks
    private JsonServiceImpl jsonService;
    @Mock
    private ConversionService conversionService;

    @Test
    public void loadFixturesByLeague() throws Exception {
        jsonService = spy(jsonService);
        final JSONObject jsonObject = mock(JSONObject.class);
        final int leagueId = 1;
        doReturn(jsonObject).when(jsonService).getObjectsFromRapidAPI(anyString());

        final JSONObject result = jsonService.loadFixturesByLeague(leagueId);

        assertThat(result, is(jsonObject));
        verify(jsonService).getObjectsFromRapidAPI("https://api-football-v1.p.mashape.com/fixtures/league/" + leagueId);
    }

    @Test
    public void loadFixturesByDate() throws Exception {
        jsonService = spy(jsonService);
        final JSONObject jsonObject = mock(JSONObject.class);
        final LocalDate date = LocalDate.now();
        final String formattedString = date.format(DateTimeFormatter.ISO_DATE);
        doReturn(jsonObject).when(jsonService).getObjectsFromRapidAPI(anyString());

        final JSONObject result = jsonService.loadFixturesByDate(date);

        assertThat(result, is(jsonObject));
        verify(jsonService).getObjectsFromRapidAPI("https://api-football-v1.p.mashape.com/fixtures/date/" + formattedString);
    }

    @Test
    public void loadLiveFixtures() throws Exception {
        jsonService = spy(jsonService);
        final JSONObject jsonObject = mock(JSONObject.class);
        doReturn(jsonObject).when(jsonService).getObjectsFromRapidAPI(anyString());

        final JSONObject result = jsonService.loadLiveFixtures();

        assertThat(result, is(jsonObject));
        verify(jsonService).getObjectsFromRapidAPI("https://api-football-v1.p.mashape.com/fixtures/live");
    }

    @Test
    public void loadTeamsByLeague() throws Exception {
        jsonService = spy(jsonService);
        final JSONObject jsonObject = mock(JSONObject.class);
        final int leagueId = 1;
        doReturn(jsonObject).when(jsonService).getObjectsFromRapidAPI(anyString());

        final JSONObject result = jsonService.loadTeamsByLeague(leagueId);

        assertThat(result, is(jsonObject));
        verify(jsonService).getObjectsFromRapidAPI("https://api-football-v1.p.rapidapi.com/teams/league/" + leagueId);
    }

    @Test
    public void loadLeagues() throws Exception {
        jsonService = spy(jsonService);
        final JSONObject jsonObject = mock(JSONObject.class);
        doReturn(jsonObject).when(jsonService).getObjectsFromRapidAPI(anyString());

        final JSONObject result = jsonService.loadLeagues();

        assertThat(result, is(jsonObject));
        verify(jsonService).getObjectsFromRapidAPI("https://api-football-v1.p.rapidapi.com/leagues");
    }

    @Test
    public void loadLeaguesOfSeason() throws Exception {
        jsonService = spy(jsonService);
        final JSONObject jsonObject = mock(JSONObject.class);
        final String year = "2019";
        doReturn(jsonObject).when(jsonService).getObjectsFromRapidAPI(anyString());

        final JSONObject result = jsonService.loadLeaguesOfSeason(year);

        assertThat(result, is(jsonObject));
        verify(jsonService).getObjectsFromRapidAPI("https://api-football-v1.p.rapidapi.com/leagues/season/" + year);
    }
}
