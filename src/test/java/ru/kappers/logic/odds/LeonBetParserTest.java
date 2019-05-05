package ru.kappers.logic.odds;

import org.junit.Test;
import ru.kappers.model.dto.leon.LeonOddsDTO;

import java.util.List;

public class LeonBetParserTest {

    @Test
    public void loadEventUrlsOfTournament() {
        BetParser parser = new LeonBetParser();
        List<String> list = parser.loadEventUrlsOfTournament("/events/Soccer/281474976710675-Europe-UEFA-Champions-League");
        List <LeonOddsDTO> eventsWithOdds = parser.getEventsWithOdds(list);

    }

    @Test
    public void loadEventOdds() {
        BetParser parser = new LeonBetParser();
        parser.loadEventOdds("/events/Soccer/281474976710675-Europe-UEFA-Champions-League/281474982732162-Ajax-Tottenham-Hotspur");
    }
}