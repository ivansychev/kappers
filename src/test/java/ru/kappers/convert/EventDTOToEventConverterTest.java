package ru.kappers.convert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kappers.model.Event;
import ru.kappers.model.Fixture;
import ru.kappers.model.dto.EventDTO;
import ru.kappers.model.utilmodel.Outcomes;
import ru.kappers.service.FixtureService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EventDTOToEventConverterTest {

    @InjectMocks
    private EventDTOToEventConverter converter;
    @Mock
    private FixtureService fixtureService;

    @Test
    public void convertMustReturnNullIfParameterIsNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convert() {
        final List<EventDTO> dtoList = Arrays.asList(
                EventDTO.builder()
                        .f_id(1)
                        .outcome(Outcomes.HOMETEAMWIN)
                        .coefficient(BigDecimal.TEN)
                        .tokens(200)
                        .price(new BigDecimal("35.5"))
                        .build(),
                EventDTO.builder()
                        .f_id(2)
                        .outcome(Outcomes.GUESTTEAMWIN)
                        .coefficient(BigDecimal.ONE)
                        .tokens(300)
                        .price(new BigDecimal("15.1"))
                        .build()
        );

        for (EventDTO dto : dtoList) {
            reset(fixtureService);
            final Fixture fixture = mock(Fixture.class);
            when(fixtureService.getById(eq(dto.getF_id()))).thenReturn(fixture);

            final Event result = converter.convert(dto);

            assertThat(result, is(notNullValue()));
            assertThat(result.getFixture(), is(fixture));
            assertThat(result.getOutcome(), is(dto.getOutcome()));
            assertThat(result.getCoefficient(), is(dto.getCoefficient()));
            assertThat(result.getTokens(), is(dto.getTokens()));
            assertThat(result.getPrice(), is(dto.getPrice()));
            verify(fixtureService).getById(eq(dto.getF_id()));
        }
    }
}