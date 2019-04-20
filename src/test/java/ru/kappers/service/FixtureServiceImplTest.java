package ru.kappers.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kappers.KappersApplication;
import ru.kappers.model.Fixture;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@Log4j
@ActiveProfiles("test")
@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KappersApplication.class})
@TestExecutionListeners({DbUnitTestExecutionListener.class})
public class FixtureServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {
private static final String STATUS_NOT_STARTED = "Not Started";
private static final String STATUS_MATCH_FINISHED = "Match Finished";
    @Autowired
    private FixtureService service;
    private Timestamp nowTstmp = new Timestamp(System.currentTimeMillis());
    private Fixture today = Fixture.builder()
            .id(111)
            .eventDate(nowTstmp)
            .eventTimestamp(nowTstmp.getTime())
            .awayTeam("Real Madrid")
            .homeTeam("FC Barselona")
            .leagueId(87)
            .status(STATUS_NOT_STARTED)
            .build();

    private Fixture tomorrow = Fixture.builder()
            .id(112)
            .eventDate(new Timestamp(nowTstmp.getTime() + 24 * 3600 * 1000))
            .eventTimestamp(nowTstmp.getTime() + 24 * 3600 * 1000)
            .awayTeam("Manchester United")
            .homeTeam("FC Liverpool")
            .leagueId(2)
            .status(STATUS_NOT_STARTED)
            .build();

    private Fixture yesterday = Fixture.builder()
            .id(110)
            .eventDate(new Timestamp(nowTstmp.getTime() - 24 * 3600 * 1000))
            .eventTimestamp(nowTstmp.getTime() - 24 * 3600 * 1000)
            .awayTeam("Paris Saint Germain")
            .homeTeam("Lyon")
            .leagueId(4)
            .status(STATUS_MATCH_FINISHED)
            .build();

    private Fixture nextWeek = Fixture.builder()
            .id(111)
            .eventDate(new Timestamp(nowTstmp.getTime() + 24 * 3600 * 1000*7))
            .eventTimestamp(nowTstmp.getTime() + 24 * 3600 * 1000*7)
            .awayTeam("Real Madrid")
            .homeTeam("FC Barselona")
            .leagueId(87)
            .status(STATUS_NOT_STARTED)
            .build();

    private Fixture lastWeek = Fixture.builder()
            .id(110)
            .eventDate(new Timestamp(nowTstmp.getTime() - 24 * 3600 * 1000*7))
            .eventTimestamp(nowTstmp.getTime() - 24 * 3600 * 1000*7)
            .awayTeam("Paris Saint Germain")
            .homeTeam("Lyon")
            .leagueId(4)
            .status(STATUS_MATCH_FINISHED)
            .build();

    @Before
    public void setUp() {
        logger.debug(today);
        logger.debug(tomorrow);
        logger.debug(yesterday);
    }

    @Test
    public void getById() {
        Fixture fixture = Fixture.builder()
                .id(54552)
                .eventDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        service.addRecord(fixture);
        Fixture byId = service.getById(54552);
        assertNotNull(byId);
    }


    @Test
    public void addRecord() {
        Fixture record = service.addRecord(tomorrow);
        assertNotNull(record);
        assertEquals((long) record.getId(), (long) 112);
        service.deleteRecord(record);
    }

    @Test
    public void deleteRecord() {
        Fixture record = service.addRecord(today);
        Assert.assertNotNull(record);
        Fixture byId = service.getById(111);
        assertEquals(byId, record);
        service.deleteRecord(today);
        byId = service.getById(111);
        assertNull(byId);
    }

    @Test
    public void deleteRecordByFixtureId() {
        Fixture record = service.addRecord(today);
        assertNotNull(record);
        Fixture byId = service.getById(111);
        assertEquals(byId, record);
        service.deleteRecordById(111);
        byId = service.getById(111);
        assertNull(byId);
    }

    @Test
    public void updateFixture() {
        service.addRecord(tomorrow);
        Fixture fixture = service.getById(tomorrow.getId());
        fixture.setStatus(STATUS_MATCH_FINISHED);
        service.updateFixture(fixture);
        Fixture updated = service.getById(tomorrow.getId());
        assertEquals(updated.getStatus(), STATUS_MATCH_FINISHED);
        service.deleteRecord(updated);
    }

    @Test
    public void getAll() {
        service.addRecord(today);
        service.addRecord(tomorrow);
        service.addRecord(yesterday);
        List<Fixture> all = service.getAll();
        assertNotNull(all);
        assertNotEquals(all.size(), 0);
        boolean containsToday = all.contains(today);
        boolean containsTomorrow = all.contains(tomorrow);
        boolean containsYesterday = all.contains(yesterday);
        assertTrue(containsToday);
        assertTrue(containsTomorrow);
        assertTrue(containsYesterday);
        service.deleteRecord(today);
        service.deleteRecord(tomorrow);
        service.deleteRecord(yesterday);
    }

    @Test
    public void getFixturesByPeriod() {
        service.addRecord(today);
        service.addRecord(tomorrow);
        service.addRecord(yesterday);
        List<Fixture> todaysFixtures = service.getFixturesByPeriod(new Timestamp(System.currentTimeMillis() - 8 * 3600 * 1000), new Timestamp(System.currentTimeMillis() + 8 * 3600 * 1000));
        assertTrue(todaysFixtures.contains(today));
        assertFalse(todaysFixtures.contains(yesterday));
        assertFalse(todaysFixtures.contains(tomorrow));
        service.deleteRecord(tomorrow);
        service.deleteRecord(yesterday);
        service.deleteRecord(today);

    }

    @Test
    public void getFixturesToday() {
        service.addRecord(today);
        List<Fixture> fixturesToday = service.getFixturesToday();
        assertTrue(fixturesToday.contains(today));
        assertFalse(fixturesToday.contains(yesterday));
        assertFalse(fixturesToday.contains(tomorrow));
        service.deleteRecord(today);
    }

    @Test
    public void getFixturesTodayFiltered() {
        service.addRecord(today);
        List<Fixture> fixturesToday = service.getFixturesToday(STATUS_NOT_STARTED);
        assertTrue(fixturesToday.contains(today));
        List<Fixture> fixturesToday2 = service.getFixturesToday(STATUS_MATCH_FINISHED);
        assertFalse(fixturesToday2.contains(today));
        service.deleteRecord(today);


    }

    @Test
    public void getFixturesLastWeek() {

    }

    @Test
    public void getFixturesLastWeekFiltered() {
    }

    @Test
    public void getFixturesNextWeek() {
    }

    @Test
    public void getFixturesNextWeekFiltered() {
    }
}