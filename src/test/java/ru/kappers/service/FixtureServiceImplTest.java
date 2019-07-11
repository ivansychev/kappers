package ru.kappers.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kappers.KappersApplication;
import ru.kappers.model.Fixture;
import ru.kappers.model.Fixture.ShortStatus;
import ru.kappers.model.Fixture.Status;
import ru.kappers.util.DateTimeUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static ru.kappers.util.DateTimeUtil.MILLISECONDS_IN_HOUR;

@Slf4j
@ActiveProfiles("test")
@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KappersApplication.class})
@TestExecutionListeners({DbUnitTestExecutionListener.class})
public class FixtureServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private FixtureService service;

    private LocalDateTime now = LocalDateTime.now();
    private Timestamp nowTstmp = Timestamp.valueOf(now);

    private Fixture today = Fixture.builder()
            .id(111)
            .eventDate(now)
            .eventTimestamp(nowTstmp.getTime())
            .awayTeam("Real Madrid")
            .homeTeam("FC Barselona")
            .leagueId(87)
            .status(Status.NOT_STARTED)
            .statusShort(ShortStatus.NOT_STARTED)
            .build();

    private Fixture todayBegin = new Fixture();
    private Fixture todayEnd = new Fixture();
    {
        Fixture it = todayBegin;
        BeanUtils.copyProperties(today, it);
        it.setId(it.getId() + 10);
        it.setEventDate(LocalDateTime.of(now.toLocalDate(), LocalTime.MIN));
        it.setEventTimestamp(Timestamp.valueOf(it.getEventDate()).getTime());
        it.setStatus(Status.MATCH_FINISHED);
        it.setStatusShort(ShortStatus.MATCH_FINISHED);

        it = todayEnd;
        BeanUtils.copyProperties(today, it);
        it.setId(it.getId() + 11);
        it.setEventDate(LocalDateTime.of(now.toLocalDate(), LocalTime.MAX));
        it.setEventTimestamp(Timestamp.valueOf(it.getEventDate()).getTime());
    }

    private Fixture tomorrow = Fixture.builder()
            .id(112)
            .eventDate(now.plusDays(1))
            .eventTimestamp(nowTstmp.getTime() + DateTimeUtil.MILLISECONDS_IN_DAY)
            .awayTeam("Manchester United")
            .homeTeam("FC Liverpool")
            .leagueId(2)
            .status(Status.NOT_STARTED)
            .statusShort(ShortStatus.NOT_STARTED)
            .build();

    private Fixture tomorrowBegin = new Fixture();
    private Fixture tomorrowEnd = new Fixture();
    {
        Fixture it = tomorrowBegin;
        BeanUtils.copyProperties(tomorrow, it);
        it.setId(it.getId() + 10);
        it.setEventDate(LocalDateTime.of(now.plusDays(1).toLocalDate(), LocalTime.MIN));
        it.setEventTimestamp(Timestamp.valueOf(it.getEventDate()).getTime());

        it = tomorrowEnd;
        BeanUtils.copyProperties(tomorrow, it);
        it.setId(it.getId() + 11);
        it.setEventDate(LocalDateTime.of(now.plusDays(1).toLocalDate(), LocalTime.MAX));
        it.setEventTimestamp(Timestamp.valueOf(it.getEventDate()).getTime());
    }

    private Fixture yesterday = Fixture.builder()
            .id(110)
            .eventDate(now.minusDays(1))
            .eventTimestamp(nowTstmp.getTime() - DateTimeUtil.MILLISECONDS_IN_DAY)
            .awayTeam("Paris Saint Germain")
            .homeTeam("Lyon")
            .leagueId(4)
            .status(Status.MATCH_FINISHED)
            .statusShort(ShortStatus.MATCH_FINISHED)
            .build();

    private Fixture yesterdayBegin = new Fixture();
    private Fixture yesterdayEnd = new Fixture();
    {
        Fixture it = yesterdayBegin;
        BeanUtils.copyProperties(yesterday, it);
        it.setId(it.getId() + 10);
        it.setEventDate(LocalDateTime.of(now.minusDays(1).toLocalDate(), LocalTime.MIN));
        it.setEventTimestamp(Timestamp.valueOf(it.getEventDate()).getTime());

        it = yesterdayEnd;
        BeanUtils.copyProperties(yesterday, it);
        it.setId(it.getId() + 11);
        it.setEventDate(LocalDateTime.of(now.minusDays(1).toLocalDate(), LocalTime.MAX));
        it.setEventTimestamp(Timestamp.valueOf(it.getEventDate()).getTime());
    }

    private Fixture nextWeek = Fixture.builder()
            .id(113)
            .eventDate(now.plusDays(7))
            .eventTimestamp(nowTstmp.getTime() + DateTimeUtil.MILLISECONDS_IN_WEEK)
            .awayTeam("Real Madrid")
            .homeTeam("FC Barselona")
            .leagueId(87)
            .status(Status.NOT_STARTED)
            .statusShort(ShortStatus.NOT_STARTED)
            .build();

    private Fixture lastWeek = Fixture.builder()
            .id(114)
            .eventDate(now.minusDays(7))
            .eventTimestamp(nowTstmp.getTime() - DateTimeUtil.MILLISECONDS_IN_WEEK)
            .awayTeam("Paris Saint Germain")
            .homeTeam("Lyon")
            .leagueId(4)
            .status(Status.MATCH_FINISHED)
            .statusShort(ShortStatus.MATCH_FINISHED)
            .build();

    protected void clearTable() {
        deleteFromTables("fixtures");
    }

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
                .eventDate(LocalDateTime.now())
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
        fixture.setStatus(Status.MATCH_FINISHED);
        service.updateFixture(fixture);
        Fixture updated = service.getById(tomorrow.getId());
        assertEquals(updated.getStatus(), Status.MATCH_FINISHED);
        service.deleteRecord(updated);
    }

    @Test
    public void getAll() {
        clearTable();
        final List<Fixture> fixtureList = Arrays.asList(today, tomorrow, yesterday);
        fixtureList.forEach(service::addRecord);

        List<Fixture> all = service.getAll();

        assertNotNull(all);
        assertThat(all.size(), is(fixtureList.size()));
        assertThat(all.containsAll(fixtureList), is(true));
    }

    @Test
    public void getAllForPageableOf2ItemsOnFirstPageAndSortASCByEventDate() {
        clearTable();
        Stream.of(today, tomorrow, yesterday).forEach(service::addRecord);
        final PageRequest pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "eventDate"));

        final Page<Fixture> all = service.getAll(pageable);

        assertThat(all, is(notNullValue()));
        assertThat(all.getContent().size(), is(pageable.getPageSize()));
        assertThat(all.getContent().containsAll(Arrays.asList(yesterday, today)), is(true));
        assertThat(all.getContent().contains(tomorrow), is(false));
    }

    @Test
    public void getFixturesByPeriod() {
        clearTable();
        Stream.of(today, tomorrow, yesterday).forEach(service::addRecord);

        final List<Fixture> todaysFixtures = service.getFixturesByPeriod(now.minusHours(8), now.plusHours(8));

        assertTrue(todaysFixtures.contains(today));
        Stream.of(tomorrow, yesterday).forEach(it -> assertFalse(todaysFixtures.contains(it)));
    }

    @Test
    public void getFixturesByPeriodForPageableOf4ItemsOnFirstPageAndSortAscByEventDate() {
        clearTable();
        Stream.of(todayBegin, today, todayEnd, tomorrow, yesterday).forEach(service::addRecord);
        final PageRequest pageable = PageRequest.of(0, 4, Sort.by(Sort.Direction.ASC, "eventDate"));

        final Page<Fixture> todaysFixtures = service.getFixturesByPeriod(todayBegin.getEventDate(), todayEnd.getEventDate(), pageable);

        assertTrue(todaysFixtures.getContent().containsAll(Arrays.asList(todayBegin, today, todayEnd)));
        Stream.of(tomorrow, yesterday).forEach(it -> assertFalse(todaysFixtures.getContent().contains(it)));
    }

    @Test
    public void getFixturesToday() {
        clearTable();
        Stream.of(today, todayBegin, todayEnd, tomorrow, yesterday).forEach(service::addRecord);

        List<Fixture> fixturesToday = service.getFixturesToday();

        assertTrue(fixturesToday.containsAll(Arrays.asList(today, todayBegin, todayEnd)));
        Stream.of(tomorrow, yesterday).forEach(it -> assertFalse(fixturesToday.contains(it)));
    }

    @Test
    public void getFixturesTodayForPageableOf2ItemsOnFirstPageAndSortDescByEventDate() {
        clearTable();
        Stream.of(today, todayBegin, todayEnd, tomorrow, yesterday).forEach(service::addRecord);
        final PageRequest pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "eventDate"));

        final Page<Fixture> fixturesToday = service.getFixturesToday(pageable);

        assertThat(fixturesToday.getContent().size(), is(pageable.getPageSize()));
        assertTrue(fixturesToday.getContent().containsAll(Arrays.asList(today, todayEnd)));
        Stream.of(todayBegin, yesterday, tomorrow)
                .forEach(it -> assertFalse(fixturesToday.getContent().contains(it)));
    }

    @Test
    public void getFixturesTodayFiltered() {
        clearTable();
        Stream.of(today, todayBegin, todayEnd, tomorrow, yesterday)
                .forEach(service::addRecord);

        final List<Fixture> fixturesToday = service.getFixturesToday(Status.NOT_STARTED);
        assertTrue(fixturesToday.containsAll(Arrays.asList(today, todayEnd)));
        Stream.of(todayBegin, tomorrow, yesterday)
                .forEach(it -> assertFalse(fixturesToday.contains(it)));

        final List<Fixture> fixturesToday2 = service.getFixturesToday(Status.MATCH_FINISHED);
        assertTrue(fixturesToday2.contains(todayBegin));
        Stream.of(today, todayEnd, tomorrow, yesterday)
                .forEach(it -> assertFalse(fixturesToday2.contains(it)));
    }

    @Test
    public void getFixturesTodayFilteredForPageableOf2ItemsOnFirstPageAndSortAscByEventDate() {
        clearTable();
        Stream.of(today, todayBegin, todayEnd, tomorrow, yesterday)
                .forEach(service::addRecord);
        final PageRequest pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "eventDate"));

        final Page<Fixture> fixturesToday = service.getFixturesToday(Status.NOT_STARTED, pageable);
        assertTrue(fixturesToday.getContent().containsAll(Arrays.asList(today, todayEnd)));
        Stream.of(todayBegin, tomorrow, yesterday)
                .forEach(it -> assertFalse(fixturesToday.getContent().contains(it)));

        final Page<Fixture> fixturesToday2 = service.getFixturesToday(Status.MATCH_FINISHED, pageable);
        assertTrue(fixturesToday2.getContent().contains(todayBegin));
        Stream.of(today, todayEnd, tomorrow, yesterday)
                .forEach(it -> assertFalse(fixturesToday2.getContent().contains(it)));
    }

    @Test
    public void getFixturesLastWeek() {
        clearTable();
        Stream.of(lastWeek, yesterday, todayBegin, today, todayEnd, tomorrow, nextWeek)
                .forEach(service::addRecord);

        List<Fixture> fixturesLastWeek = service.getFixturesLastWeek();

        assertTrue(fixturesLastWeek.containsAll(Arrays.asList(lastWeek, yesterday, todayBegin, today, todayEnd)));
        Stream.of(tomorrow, nextWeek)
                .forEach(it -> assertFalse(fixturesLastWeek.contains(it)));
    }

    @Test
    public void getFixturesLastWeekForPageableOf4ItemsOnFirstPageAndSortAscByEventDate() {
        clearTable();
        Stream.of(lastWeek, yesterday, todayBegin, today, todayEnd, tomorrow, nextWeek)
                .forEach(service::addRecord);
        final PageRequest pageable = PageRequest.of(0, 4, Sort.by(Sort.Direction.ASC, "eventDate"));

        Page<Fixture> fixturesLastWeek = service.getFixturesLastWeek(pageable);

        assertTrue(fixturesLastWeek.getContent().containsAll(Arrays.asList(lastWeek, yesterday, todayBegin, today)));
        Stream.of(todayEnd, tomorrow, nextWeek)
                .forEach(it -> assertFalse(fixturesLastWeek.getContent().contains(it)));
    }

    @Test
    public void getFixturesLastWeekFiltered() {
        clearTable();
        Stream.of(lastWeek, yesterday, todayBegin, today, todayEnd, tomorrow, nextWeek)
                .forEach(service::addRecord);

        List<Fixture> fixturesLastWeek = service.getFixturesLastWeek(Status.MATCH_FINISHED);

        assertTrue(fixturesLastWeek.containsAll(Arrays.asList(lastWeek, yesterday, todayBegin)));
        Stream.of(today, todayEnd, tomorrow, nextWeek)
                .forEach(it -> assertFalse(fixturesLastWeek.contains(it)));
    }

    @Test
    public void getFixturesLastWeekFilteredForPageableOf2ItemsOnFirstPageAndSortAscByEventDate() {
        clearTable();
        Stream.of(lastWeek, yesterday, todayBegin, today, todayEnd, tomorrow, nextWeek)
                .forEach(service::addRecord);
        final PageRequest pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "eventDate"));

        Page<Fixture> fixturesLastWeek = service.getFixturesLastWeek(Status.MATCH_FINISHED, pageable);

        assertTrue(fixturesLastWeek.getContent().containsAll(Arrays.asList(lastWeek, yesterday)));
        Stream.of(todayBegin, today, todayEnd, tomorrow, nextWeek)
                .forEach(it -> assertFalse(fixturesLastWeek.getContent().contains(it)));
    }

    @Test
    public void getFixturesNextWeek() {
        clearTable();
        Stream.of(lastWeek, yesterday, todayBegin, today, todayEnd, tomorrow, nextWeek)
                .forEach(service::addRecord);

        List<Fixture> fixturesNextWeek = service.getFixturesNextWeek();

        assertTrue(fixturesNextWeek.containsAll(Arrays.asList(todayBegin, today, todayEnd, tomorrow, nextWeek)));
        Stream.of(lastWeek, yesterday)
                .forEach(it -> assertFalse(fixturesNextWeek.contains(it)));
    }

    @Test
    public void getFixturesNextWeekForPageableOf2ItemsOnSecondPageAndSortAscByEventDate() {
        clearTable();
        Stream.of(lastWeek, yesterday, todayBegin, today, todayEnd, tomorrow, nextWeek)
                .forEach(service::addRecord);
        final PageRequest pageable = PageRequest.of(1, 2, Sort.by(Sort.Direction.ASC, "eventDate"));

        Page<Fixture> fixturesNextWeek = service.getFixturesNextWeek(pageable);

        assertTrue(fixturesNextWeek.getContent().containsAll(Arrays.asList(todayEnd, tomorrow)));
        Stream.of(lastWeek, yesterday, todayBegin, today, nextWeek)
                .forEach(it -> assertFalse(fixturesNextWeek.getContent().contains(it)));
    }

    @Test
    public void getFixturesNextWeekFiltered() {
        clearTable();
        Stream.of(lastWeek, yesterday, todayBegin, today, todayEnd, tomorrow, nextWeek)
                .forEach(service::addRecord);

        List<Fixture> fixturesNextWeek = service.getFixturesNextWeek(Status.NOT_STARTED);

        assertTrue(fixturesNextWeek.containsAll(Arrays.asList(today, todayEnd, tomorrow, nextWeek)));
        Stream.of(lastWeek, yesterday, todayBegin)
                .forEach(it -> assertFalse(fixturesNextWeek.contains(it)));
    }

    @Test
    public void getFixturesNextWeekFilteredForPageableOf2ItemsOnSecondPageAndSortAscByEventDate() {
        clearTable();
        Stream.of(lastWeek, yesterday, todayBegin, today, todayEnd, tomorrow, nextWeek)
                .forEach(service::addRecord);
        final PageRequest pageable = PageRequest.of(1, 2, Sort.by(Sort.Direction.ASC, "eventDate"));

        Page<Fixture> fixturesNextWeek = service.getFixturesNextWeek(Status.NOT_STARTED, pageable);

        assertTrue(fixturesNextWeek.getContent().containsAll(Arrays.asList(tomorrow, nextWeek)));
        Stream.of(lastWeek, yesterday, todayBegin, today, todayEnd)
                .forEach(it -> assertFalse(fixturesNextWeek.getContent().contains(it)));
    }
}