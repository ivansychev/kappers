package ru.kappers.convert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kappers.model.Fixture;
import ru.kappers.model.dto.rapidapi.FixtureRapidDTO;
import ru.kappers.util.DateTimeUtil;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FixtureRapidDTOToFixtureConverterTest {

    @InjectMocks
    private FixtureRapidDTOToFixtureConverter converter;

    @Test
    public void convertMustReturnNullIfParameterIsNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convert() {
        final List<FixtureRapidDTO> dtoList = Arrays.asList(
                FixtureRapidDTO.builder()
                        .fixture_id(1)
                        .event_timestamp(1L)
                        .event_date("2020-12-02T10:15:30+01:00")
                        .league_id(1)
                        .round("0")
                        .homeTeam_id(1)
                        .awayTeam_id(2)
                        .homeTeam("ЦСК")
                        .awayTeam("Спартак")
                        .status(Fixture.Status.NOT_STARTED.getValue())
                        .statusShort(Fixture.ShortStatus.NOT_STARTED.getValue())
                        .goalsHomeTeam(0)
                        .goalsAwayTeam(0)
                        .halftime_score("")
                        .final_score("")
                        .penalty("")
                        .elapsed(0)
                        .firstHalfStart(0L)
                        .secondHalfStart(0L)
                        .build(),
                FixtureRapidDTO.builder()
                        .fixture_id(2)
                        .event_timestamp(2L)
                        .event_date("2019-03-02T10:15:30+01:00")
                        .league_id(2)
                        .round("1")
                        .homeTeam_id(3)
                        .awayTeam_id(4)
                        .homeTeam("Факел")
                        .awayTeam("Динамо")
                        .status(Fixture.Status.MATCH_FINISHED.getValue())
                        .statusShort(Fixture.ShortStatus.MATCH_FINISHED.getValue())
                        .goalsHomeTeam(1)
                        .goalsAwayTeam(2)
                        .halftime_score("0:1")
                        .final_score("1:2")
                        .penalty("1")
                        .elapsed(1)
                        .firstHalfStart(1L)
                        .secondHalfStart(2L)
                        .build()
        );

        for (FixtureRapidDTO dto : dtoList) {
            final Fixture fixture = converter.convert(dto);

            assertThat(fixture, is(notNullValue()));
            assertThat(fixture.getId(), is(dto.getFixture_id()));
            assertThat(fixture.getEventTimestamp(), is(dto.getEvent_timestamp()));
            assertThat(fixture.getEventDate(), is(DateTimeUtil.parseLocalDateTimeFromZonedDateTime(dto.getEvent_date())));
            assertThat(fixture.getLeagueId(), is(dto.getLeague_id()));
            assertThat(fixture.getRound(), is(dto.getRound()));
            assertThat(fixture.getHomeTeamId(), is(dto.getHomeTeam_id()));
            assertThat(fixture.getAwayTeamId(), is(dto.getAwayTeam_id()));
            assertThat(fixture.getHomeTeam(), is(dto.getHomeTeam()));
            assertThat(fixture.getAwayTeam(), is(dto.getAwayTeam()));
            assertThat(fixture.getStatus(), is(Fixture.Status.byValue(dto.getStatus())));
            assertThat(fixture.getStatusShort(), is(Fixture.ShortStatus.byValue(dto.getStatusShort())));
            assertThat(fixture.getGoalsHomeTeam(), is(dto.getGoalsHomeTeam()));
            assertThat(fixture.getGoalsAwayTeam(), is(dto.getGoalsAwayTeam()));
            assertThat(fixture.getHalftimeScore(), is(dto.getHalftime_score()));
            assertThat(fixture.getFinalScore(), is(dto.getFinal_score()));
            assertThat(fixture.getPenalty(), is(dto.getPenalty()));
            assertThat(fixture.getElapsed(), is(dto.getElapsed()));
            assertThat(fixture.getFirstHalfStart(), is(dto.getFirstHalfStart()));
            assertThat(fixture.getSecondHalfStart(), is(dto.getSecondHalfStart()));
        }
    }
}