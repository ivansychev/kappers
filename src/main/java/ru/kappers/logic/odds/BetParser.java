package ru.kappers.logic.odds;

import java.net.MalformedURLException;
import java.util.List;

public interface BetParser<T> {
    List<String> loadUrlsOfEvents(String url) throws MalformedURLException;
    List<T> loadEventsOfTournament(String url);

}
