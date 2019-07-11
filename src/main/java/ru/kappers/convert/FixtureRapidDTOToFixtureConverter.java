package ru.kappers.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.kappers.model.Fixture;
import ru.kappers.model.dto.rapidapi.FixtureRapidDTO;
import ru.kappers.util.DateTimeUtil;

import javax.annotation.Nullable;

/**
 * Конвертер из {@link FixtureRapidDTO} в {@link Fixture}
 */
@Service
public class FixtureRapidDTOToFixtureConverter implements Converter<FixtureRapidDTO, Fixture> {

    @Nullable
    @Override
    public Fixture convert(@Nullable FixtureRapidDTO dto) {
        if (dto == null) {
            return null;
        }
        final Fixture fixture = Fixture.builder()
            .id(dto.getFixture_id())
            .eventTimestamp(dto.getEvent_timestamp())
            .eventDate(DateTimeUtil.parseLocalDateTimeFromZonedDateTime(dto.getEvent_date()))
            .leagueId(dto.getLeague_id())
            .round(dto.getRound())
            .homeTeamId(dto.getHomeTeam_id())
            .awayTeamId(dto.getAwayTeam_id())
            .homeTeam(dto.getHomeTeam())
            .awayTeam(dto.getAwayTeam())
            .status(Fixture.Status.byValue(dto.getStatus()))
            .statusShort(Fixture.ShortStatus.byValue(dto.getStatusShort()))
            .goalsHomeTeam(dto.getGoalsHomeTeam())
            .goalsAwayTeam(dto.getGoalsAwayTeam())
            .halftimeScore(dto.getHalftime_score())
            .finalScore(dto.getFinal_score())
            .penalty(dto.getPenalty())
            .elapsed(dto.getElapsed())
            .firstHalfStart(dto.getFirstHalfStart())
            .secondHalfStart(dto.getSecondHalfStart())
            .build();
        return fixture;
    }
}
