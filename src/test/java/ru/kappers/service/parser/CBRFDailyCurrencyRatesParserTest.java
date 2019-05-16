package ru.kappers.service.parser;

import org.apache.tomcat.util.digester.DocumentProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.ResourceUtils;
import ru.kappers.model.CurrencyRate;
import ru.kappers.util.DateTimeUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CBRFDailyCurrencyRatesParserTest {
    @InjectMocks
    private CBRFDailyCurrencyRatesParser currencyRatesParser = new CBRFDailyCurrencyRatesParser();

    private final String testJSON;

    public CBRFDailyCurrencyRatesParserTest() throws IOException {
        File file = ResourceUtils.getFile("classpath:data/cbr_daily_json.js");
        testJSON = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
    }

    @Test
    public void defaultConstructor() throws MalformedURLException {
        currencyRatesParser = new CBRFDailyCurrencyRatesParser();

        assertThat(currencyRatesParser.getCbrfUrl().toString(), is(CBRFDailyCurrencyRatesParser.CBRF_DAILY_JSON_DEFAULT_URL));
    }

    @Test
    public void parseFromCBRFMustCallParseFromURL() {
        final URL cbrfUrl = currencyRatesParser.getCbrfUrl();
        currencyRatesParser = spy(currencyRatesParser);
        final List<CurrencyRate> currencyRates = Arrays.asList(mock(CurrencyRate.class));
        doReturn(currencyRates).when(currencyRatesParser).parseFromURL(cbrfUrl);

        final List<CurrencyRate> result = currencyRatesParser.parseFromCBRF();

        assertThat(result, is(currencyRates));
        verify(currencyRatesParser).parseFromURL(cbrfUrl);
    }

    @Test
    public void parseFromURL() {
        final URL cbrfUrl = currencyRatesParser.getCbrfUrl();
        currencyRatesParser = spy(currencyRatesParser);
        doReturn(testJSON).when(currencyRatesParser).getJSONStringFromURL(cbrfUrl);

        final List<CurrencyRate> result = currencyRatesParser.parseFromURL(cbrfUrl);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(34));
        verify(currencyRatesParser).getJSONStringFromURL(cbrfUrl);
        verify(currencyRatesParser).parseFromJSON(testJSON);
    }

    @Test
    public void parseFromJSON() {
        final List<CurrencyRate> currencyRates = currencyRatesParser.parseFromJSON(testJSON);

        assertThat(currencyRates, is(notNullValue()));
        assertThat(currencyRates.size(), is(34));

        final CurrencyRate usdCurrencyRate = currencyRates.stream()
                .filter(currencyRate -> "USD".equals(currencyRate.getCharCode()))
                .findFirst()
                .orElse(null);

        assertThat(usdCurrencyRate, is(notNullValue()));
        assertThat(usdCurrencyRate.getNumCode(), is("840"));
        assertThat(usdCurrencyRate.getCharCode(), is("USD"));
        assertThat(usdCurrencyRate.getName(), is("Доллар США"));
        assertThat(usdCurrencyRate.getDate(), is(DateTimeUtil.parseSqlDateFromZonedDateTime("2019-05-17T11:30:00+03:00")));
        assertThat(usdCurrencyRate.getNominal(), is(1));
        assertThat(usdCurrencyRate.getValue(), is(new BigDecimal("64.5598")));
    }
}