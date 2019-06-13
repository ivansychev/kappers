package ru.kappers.convert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kappers.model.catalog.League;
import ru.kappers.model.dto.rapidapi.LeagueRapidDTO;
import ru.kappers.util.DateTimeUtil;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class LeagueRapidDTOLeagueConverterTest {

    @InjectMocks
    private LeagueRapidDTOLeagueConverter converter;

    @Test
    public void convertMustReturnNullIfParameterIsNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convert() {
        final List<LeagueRapidDTO> dtoList = Arrays.asList(
                LeagueRapidDTO.builder()
                        .league_id(1)
                        .country("Великобритания")
                        .name("Английская футбольная Лига один")
                        .logo("https://upload.wikimedia.org/wikipedia/ru/thumb/c/cb/EFL_League_One.svg/600px-EFL_League_One.svg.png")
                        .season("Английская футбольная Лига один 2017/2018")
                        .season_start("2017-08-05")
                        .season_end("2018-05-05")
                        .build(),
                LeagueRapidDTO.builder()
                        .league_id(2)
                        .country("Российская Федерация")
                        .name("Российская Премьер-лига")
                        .logo("https://upload.wikimedia.org/wikipedia/ru/thumb/9/92/Russian_Premier_League_Logo.png/462px-Russian_Premier_League_Logo.png")
                        .season("Чемпионат России по футболу 2017/2018")
                        .season_start("2017-07-15")
                        .season_end("2018-05-13")
                        .build()
        );

        for (LeagueRapidDTO dto : dtoList) {
            final League league = converter.convert(dto);

            assertThat(league, is(notNullValue()));
            assertThat(league.getId(), is(dto.getLeague_id()));
            assertThat(league.getCountry(), is(dto.getCountry()));
            assertThat(league.getName(), is(dto.getName()));
            assertThat(league.getLogoUrl(), is(dto.getLogo()));
            assertThat(league.getSeason(), is(dto.getSeason()));
            assertThat(league.getSeasonStart(), is(DateTimeUtil.parseTimestampFromDate(dto.getSeason_start()+"+03:00")));
            assertThat(league.getSeasonEnd(), is(DateTimeUtil.parseTimestampFromDate(dto.getSeason_end()+"+03:00")));
        }
    }
}