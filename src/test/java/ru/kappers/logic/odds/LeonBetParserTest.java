package ru.kappers.logic.odds;

import org.junit.Test;

import java.net.MalformedURLException;

import static org.junit.Assert.*;

public class LeonBetParserTest {

    @Test
    public void loadUrlsOfEvents() throws MalformedURLException {
        BetParser parser = new LeonBetParser();
        parser.loadUrlsOfEvents("/events/Soccer/281474976710675-Europe-UEFA-Champions-League");
    }

    @Test
    public void loadEventsOfTournament() {
    }
}