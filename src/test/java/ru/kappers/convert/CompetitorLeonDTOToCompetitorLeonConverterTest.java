package ru.kappers.convert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kappers.model.dto.leon.CompetitorLeonDTO;
import ru.kappers.model.leonmodels.CompetitorLeon;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CompetitorLeonDTOToCompetitorLeonConverterTest {
    @InjectMocks
    private CompetitorLeonDTOToCompetitorLeonConverter converter;

    @Test
    public void convertMustReturnNullIfParameterIsNull() {
        assertThat(converter.convert(null), is(nullValue()));
    }

    @Test
    public void convert() {
        List<CompetitorLeonDTO> dtoList = Arrays.asList(
                CompetitorLeonDTO.builder()
                        .id(1L)
                        .logo("http://test1.jpg")
                        .name("test name1")
                        .build(),
                CompetitorLeonDTO.builder()
                        .id(2L)
                        .logo("http://test2.jpg")
                        .name("test name2")
                        .build()
        );

        for (CompetitorLeonDTO dto : dtoList) {
            final CompetitorLeon result = converter.convert(dto);

            assertThat(result, is(notNullValue()));
            assertThat(result.getId(), is(dto.getId()));
            assertThat(result.getLogo(), is(dto.getLogo()));
            assertThat(result.getName(), is(dto.getName()));
        }
    }
}