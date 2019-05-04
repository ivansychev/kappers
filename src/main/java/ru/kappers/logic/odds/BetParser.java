package ru.kappers.logic.odds;

import java.net.MalformedURLException;
import java.util.List;

public interface BetParser<T> {
    List<String> loadEventUrlsOfTournament(String url);
    T loadEventOdds(String url);

}
