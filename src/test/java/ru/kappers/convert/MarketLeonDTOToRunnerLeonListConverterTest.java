package ru.kappers.convert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;
import ru.kappers.model.leonmodels.RunnerLeon;
import ru.kappers.service.MarketLeonService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MarketLeonDTOToRunnerLeonListConverterTest {
    @InjectMocks
    private MarketLeonDTOToRunnerLeonListConverter converter;
    @Mock
    private MarketLeonService marketService;
    @Mock
    private ConversionService conversionService;

    @Test
    public void convertMustReturnImptyListIfParameterIsNull() {
        final List<RunnerLeon> result = converter.convert(null);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(0));
    }
}