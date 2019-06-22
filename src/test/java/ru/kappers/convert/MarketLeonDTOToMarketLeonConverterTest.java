package ru.kappers.convert;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kappers.model.dto.leon.MarketLeonDTO;
import ru.kappers.model.leonmodels.MarketLeon;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MarketLeonDTOToMarketLeonConverterTest {
    @InjectMocks
    private MarketLeonDTOToMarketLeonConverter converter;

    @Test
    public void convertMustReturnNullIfParameterIsNull() {
        assertThat(converter.convert(null), is(nullValue()));
    }

    @Test
    public void convert() {
        final List<MarketLeonDTO> dtoList = Arrays.asList(
                MarketLeonDTO.builder()
                        .id(1L)
                        .name("name 1")
                        .open(true)
                        .build(),
                MarketLeonDTO.builder()
                        .id(2L)
                        .name("name 2")
                        .open(false)
                        .build()
        );

        for (MarketLeonDTO dto : dtoList) {
            final MarketLeon result = converter.convert(dto);

            assertThat(result, is(notNullValue()));
            assertThat(result.getId(), is(dto.getId()));
            assertThat(result.getName(), is(dto.getName()));
            assertThat(result.isOpen(), is(dto.isOpen()));
        }
    }
}