package ru.kappers.convert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kappers.model.dto.leon.LeagueLeonDTO;
import ru.kappers.model.dto.leon.SportLeonDTO;
import ru.kappers.model.leonmodels.LeagueLeon;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class LeagueLeonDTOToLeagueLeonConverterTest {
    @InjectMocks
    private LeagueLeonDTOToLeagueLeonConverter converter;

    @Test
    public void convertMustReturnNullIfParameterIsNull() {
        assertThat(converter.convert(null), is(nullValue()));
    }

    @Test
    public void convert() {
        final List<LeagueLeonDTO> dtoList = Arrays.asList(
                LeagueLeonDTO.builder()
                        .id(1L)
                        .name("test name1")
                        .url("http://url1/")
                        .sport(SportLeonDTO.builder()
                                .name("test sport name1")
                                .build())
                        .build(),
                LeagueLeonDTO.builder()
                        .id(2L)
                        .name("test name2")
                        .url("http://url2/")
                        .sport(SportLeonDTO.builder()
                                .name("test sport name2")
                                .build())
                        .build()
        );

        for (LeagueLeonDTO dto : dtoList) {
            final LeagueLeon result = converter.convert(dto);

            assertThat(result, is(notNullValue()));
            assertThat(result.getId(), is(dto.getId()));
            assertThat(result.getName(), is(dto.getName()));
            assertThat(result.getUrl(), is(dto.getUrl()));
            assertThat(result.getSport(), is(dto.getSport().getName()));
        }
    }
}