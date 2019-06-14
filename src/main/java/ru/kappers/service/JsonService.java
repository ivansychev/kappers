package ru.kappers.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import ru.kappers.model.Fixture;

import java.time.LocalDate;
import java.util.Map;

public interface JsonService {
    JSONObject loadFixturesByLeague(int leagueId) throws UnirestException;

    JSONObject loadFixturesByDate(LocalDate date) throws UnirestException;

    JSONObject loadLiveFixtures() throws UnirestException;

    JSONObject loadTeamsByLeague(Integer leagueId) throws UnirestException;

    JSONObject loadLeagues() throws UnirestException;

    JSONObject loadLeaguesOfSeason(String year) throws UnirestException;

    Map<Integer, Fixture> getFixturesFromJson(String object);

    Map<Integer, Fixture> getFixturesFromFile(String filePath);
}
