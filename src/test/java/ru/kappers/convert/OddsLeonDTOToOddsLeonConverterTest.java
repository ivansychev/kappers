package ru.kappers.convert;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;
import ru.kappers.model.dto.leon.CompetitorLeonDTO;
import ru.kappers.model.dto.leon.LeagueLeonDTO;
import ru.kappers.model.dto.leon.OddsLeonDTO;
import ru.kappers.model.leonmodels.CompetitorLeon;
import ru.kappers.model.leonmodels.LeagueLeon;
import ru.kappers.model.leonmodels.OddsLeon;
import ru.kappers.service.CompetitorLeonService;
import ru.kappers.service.LeagueLeonService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OddsLeonDTOToOddsLeonConverterTest {
    @InjectMocks
    private OddsLeonDTOToOddsLeonConverter converter;
    @Mock
    private CompetitorLeonService competitorService;
    @Mock
    private LeagueLeonService leagueService;
    @Mock
    private ConversionService conversionService;

    @Test
    public void convertMustReturnNullIfParameterIsNull() {
        assertThat(converter.convert(null), is(nullValue()));
    }

    @Test
    public void convert() {
        final String compName1 = "Участник 1";
        final String compName2 = "Участник 2";
        final CompetitorLeon competitor1 = mock(CompetitorLeon.class);
        final CompetitorLeon competitor2 = mock(CompetitorLeon.class);
        final LeagueLeon league = mock(LeagueLeon.class);
        final List<CompetitorLeonDTO> competitorLeonDTOList = Arrays.asList(
                CompetitorLeonDTO.builder()
                        .name(compName1)
                        .homeAway("HOME")
                        .build(),
                CompetitorLeonDTO.builder()
                        .name(compName2)
                        .homeAway("AWAY")
                        .build()
        );
        final LeagueLeonDTO leagueLeonDTO = LeagueLeonDTO.builder().
                name("League 1")
                .build();

        final List<OddsLeonDTO> dtoList = Arrays.asList(
                OddsLeonDTO.builder()
                        .competitors(competitorLeonDTOList)
                        .league(leagueLeonDTO)
                        .id(1L)
                        .name("odd 1")
                        .kickoff(1L)
                        .open(true)
                        .url("http://url1/")
                        .lastUpdated(100L)
                        .build(),
                OddsLeonDTO.builder()
                        .competitors(competitorLeonDTOList)
                        .league(leagueLeonDTO)
                        .id(2L)
                        .name("odd 2")
                        .kickoff(10L)
                        .open(true)
                        .url("http://url2/")
                        .lastUpdated(1000L)
                        .build()
        );

        for (OddsLeonDTO dto : dtoList) {
            reset(competitorService, conversionService, leagueService);
            when(competitorService.save(any())).thenAnswer(it -> it.getArgument(0));
            when(conversionService.convert(any(CompetitorLeonDTO.class), eq(CompetitorLeon.class))).thenAnswer(it -> {
                CompetitorLeonDTO dtoInner = it.getArgument(0);
                return dtoInner.getName().equals(compName1) ? competitor1 : competitor2;
            });
            when(leagueService.save(any())).thenAnswer(it -> it.getArgument(0));
            when(conversionService.convert(any(LeagueLeonDTO.class), eq(LeagueLeon.class))).thenReturn(league);

            final OddsLeon result = converter.convert(dto);

            assertThat(result, is(notNullValue()));
            assertThat(result.getId(), is(dto.getId()));
            assertThat(result.getName(), is(dto.getName()));
            assertThat(result.getKickoff(), is(LocalDateTime.ofInstant(Instant.ofEpochMilli(dto.getKickoff()),
                    TimeZone.getDefault().toZoneId())));
            assertThat(result.isOpen(), is(dto.isOpen()));
            assertThat(result.getUrl(), is(dto.getUrl()));
            assertThat(result.getLastUpdated(), is(LocalDateTime.ofInstant(Instant.ofEpochMilli(dto.getLastUpdated()),
                    TimeZone.getDefault().toZoneId())));
            assertThat(result.getLeague(), is(league));
            assertThat(result.getHome(), is(competitor1));
            assertThat(result.getAway(), is(competitor2));
            assertThat(result.getRunners(), is(notNullValue()));
            verify(competitorService).getByName(compName1);
            verify(competitorService).getByName(compName2);
            verify(conversionService, times(competitorLeonDTOList.size())).convert(any(CompetitorLeonDTO.class), eq(CompetitorLeon.class));
            verify(competitorService, times(competitorLeonDTOList.size())).save(any());
            verify(leagueService).getByName(eq(dto.getLeague().getName()));
            verify(conversionService).convert(any(LeagueLeonDTO.class), eq(LeagueLeon.class));
            verify(leagueService).save(any());
        }
    }
}