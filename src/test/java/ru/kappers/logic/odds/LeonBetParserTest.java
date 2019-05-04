package ru.kappers.logic.odds;

import org.junit.Test;

public class LeonBetParserTest {

    @Test
    public void loadEventUrlsOfTournament() {
        BetParser parser = new LeonBetParser();
        parser.loadEventUrlsOfTournament("/events/Soccer/281474976710675-Europe-UEFA-Champions-League");
    }

    @Test
    public void loadEventOdds() {
        BetParser parser = new LeonBetParser();
        parser.loadEventOdds("/events/Soccer/281474976710675-Europe-UEFA-Champions-League/281474982732162-Ajax-Tottenham-Hotspur");
    }
}