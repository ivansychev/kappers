package ru.kappers.service.impl;

import org.joda.money.CurrencyUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kappers.config.KappersProperties;
import ru.kappers.exceptions.CurrRateGettingException;
import ru.kappers.model.CurrencyRate;
import ru.kappers.service.CurrRateService;
import ru.kappers.service.MessageTranslator;
import ru.kappers.service.parser.CBRFDailyCurrencyRatesParser;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceImplTest {
    @InjectMocks
    private CurrencyServiceImpl currencyService;
    @Mock
    private CurrRateService currRateService;
    @Mock
    private CBRFDailyCurrencyRatesParser currencyRatesParser;
    @Spy
    private KappersProperties kappersProperties;
    @Mock
    private MessageTranslator translator;

    @Test
    public void refreshCurrencyRatesForTodayMustSaveRateIfTableHasNotRate() {
        final Date currentDate = Date.valueOf(LocalDate.now());
        final String usdCharCode = "USD";
        final CurrencyRate currencyRate = CurrencyRate.builder()
                .date(currentDate)
                .charCode(usdCharCode)
                .build();
        final List<CurrencyRate> currencyRates = Arrays.asList(currencyRate);
        when(currencyRatesParser.parseFromCBRF()).thenReturn(currencyRates);
        when(currRateService.isExist(currentDate, usdCharCode)).thenReturn(false);

        currencyService.refreshCurrencyRatesForToday();

        verify(currencyRatesParser).parseFromCBRF();
        verify(currRateService).isExist(currentDate, usdCharCode);
        verify(currRateService).save(currencyRate);
    }

    @Test
    public void refreshCurrencyRatesForTodayIfTableHasRate() {
        final Date currentDate = Date.valueOf(LocalDate.now());
        final String usdCharCode = "USD";
        final CurrencyRate currencyRate = CurrencyRate.builder()
                .date(currentDate)
                .charCode(usdCharCode)
                .build();
        final List<CurrencyRate> currencyRates = Arrays.asList(currencyRate);
        when(currencyRatesParser.parseFromCBRF()).thenReturn(currencyRates);
        when(currRateService.isExist(currentDate, usdCharCode)).thenReturn(true);

        currencyService.refreshCurrencyRatesForToday();

        verify(currencyRatesParser).parseFromCBRF();
        verify(currRateService).isExist(currentDate, usdCharCode);
        verify(currRateService, never()).save(currencyRate);
    }

    @Test(expected = CurrRateGettingException.class)
    public void refreshCurrencyRatesForTodayMustThrowExceptionIfParserThrowAnyException() {
        when(currencyRatesParser.parseFromCBRF()).thenThrow(RuntimeException.class);

        currencyService.refreshCurrencyRatesForToday();
    }

    @Test
    public void refreshCurrencyRatesForTodayBySchedulerIfRefreshEnabled() {
        KappersProperties.CurrencyRates currencyRatesProp = mock(KappersProperties.CurrencyRates.class);
        when(kappersProperties.getCurrencyRates()).thenReturn(currencyRatesProp);
        when(currencyRatesProp.isRefreshCronEnabled()).thenReturn(true);
        currencyService = spy(currencyService);

        currencyService.refreshCurrencyRatesForTodayByScheduler();

        verify(currencyService).refreshCurrencyRatesForToday();
    }

    @Test
    public void refreshCurrencyRatesForTodayBySchedulerIfRefreshDisabled() {
        KappersProperties.CurrencyRates currencyRatesProp = mock(KappersProperties.CurrencyRates.class);
        when(kappersProperties.getCurrencyRates()).thenReturn(currencyRatesProp);
        when(currencyRatesProp.isRefreshCronEnabled()).thenReturn(false);
        currencyService = spy(currencyService);

        currencyService.refreshCurrencyRatesForTodayByScheduler();

        verify(currencyService, never()).refreshCurrencyRatesForToday();
    }

    @Test
    public void refreshCurrencyRatesForTodayBySchedulerIfRefreshEnabledAndFail() {
        KappersProperties.CurrencyRates currencyRatesProp = mock(KappersProperties.CurrencyRates.class);
        when(kappersProperties.getCurrencyRates()).thenReturn(currencyRatesProp);
        when(currencyRatesProp.isRefreshCronEnabled()).thenReturn(true);
        currencyService = spy(currencyService);
        doThrow(RuntimeException.class).when(currencyService).refreshCurrencyRatesForToday();

        currencyService.refreshCurrencyRatesForTodayByScheduler();

        verify(currencyService).refreshCurrencyRatesForToday();
    }

    @Test
    public void getActualCurrencyRateDateMustReturnDateFromParameterIfBothCurrencyExists() {
        final Date date = Date.valueOf(LocalDate.now());
        final String from = CurrencyUnit.EUR.getCode();
        final String to = CurrencyUnit.USD.getCode();
        final boolean currRatesGotToday = true;
        when(currRateService.isExist(date, from)).thenReturn(true);
        when(currRateService.isExist(date, to)).thenReturn(true);
        currencyService = spy(currencyService);

        final Date result = currencyService.getActualCurrencyRateDate(date, from, to, currRatesGotToday);

        assertThat(result, is(date));
        verify(currRateService).isExist(date, from);
        verify(currRateService).isExist(date, to);
        verify(currencyService, never()).refreshCurrencyRatesForToday();
    }

    @Test
    public void getActualCurrencyRateDateMustReturnDateFromParameterIfCurrencyNotExistsAndRefreshFail() {
        final Date date = Date.valueOf(LocalDate.now());
        final String from = CurrencyUnit.EUR.getCode();
        final String to = CurrencyUnit.USD.getCode();
        final boolean currRatesGotToday = false;
        when(currRateService.isExist(date, from)).thenReturn(false);
        currencyService = spy(currencyService);
        doReturn(false).when(currencyService).refreshCurrencyRatesForToday();

        final Date result = currencyService.getActualCurrencyRateDate(date, from, to, currRatesGotToday);

        assertThat(result, is(date));
        verify(currRateService).isExist(date, from);
        verify(currRateService, never()).isExist(date, to);
        verify(currencyService).refreshCurrencyRatesForToday();
    }

    @Test
    public void getActualCurrencyRateDateMustReturnDateFromParameterIfCurrencyNotExistsAndRefreshSuccess() {
        final LocalDate now = LocalDate.now();
        final Date date = Date.valueOf(now);
        final Date date2 = Date.valueOf(now.minusDays(1));
        final String from = CurrencyUnit.EUR.getCode();
        final String to = CurrencyUnit.USD.getCode();
        final boolean currRatesGotToday = false;
        when(currRateService.isExist(date, from)).thenReturn(false);
        when(currRateService.isExist(date2, from)).thenReturn(true);
        when(currRateService.isExist(date2, to)).thenReturn(true);
        currencyService = spy(currencyService);
        doReturn(true).when(currencyService).refreshCurrencyRatesForToday();

        final Date result = currencyService.getActualCurrencyRateDate(date, from, to, currRatesGotToday);

        assertThat(result, is(date2));
        verify(currRateService, times(2)).isExist(date, from);
        verify(currRateService, never()).isExist(date, to);
        verify(currRateService).isExist(date2, from);
        verify(currRateService).isExist(date2, to);
        verify(currencyService).refreshCurrencyRatesForToday();
        verify(currencyService).getActualCurrencyRateDate(date2, from, to, true);
        verify(currencyService, times(2)).getActualCurrencyRateDate(any(), eq(from), eq(to), anyBoolean());
    }

    @Test
    public void exchangeWithCurrencyUnitParameterTypes() {
        final CurrencyUnit from = CurrencyUnit.EUR;
        final CurrencyUnit to = CurrencyUnit.USD;
        final BigDecimal amount = BigDecimal.ONE;
        final BigDecimal expectedValue = new BigDecimal("1.35");
        currencyService = spy(currencyService);
        doReturn(expectedValue).when(currencyService).exchange(from.getCode(), to.getCode(), amount);

        final BigDecimal result = currencyService.exchange(from, to, amount);

        assertThat(result, is(expectedValue));
        verify(currencyService).exchange(from.getCode(), to.getCode(), amount);
    }

    @Test
    public void exchangeStringCurrencyParametersAreEqual() {
        final String from = CurrencyUnit.EUR.getCode();
        final String to = from;
        final BigDecimal amount = BigDecimal.TEN;
        currencyService = spy(currencyService);

        final BigDecimal result = currencyService.exchange(from, to, amount);

        assertThat(result, is(amount));
        verify(currencyService, never()).getActualCurrencyRateDate(any(), any(), any(), anyBoolean());
        verify(currRateService, never()).getCurrByDate(any(), any());
    }

    @Test
    public void exchangeOneEURToUSD() {
        final Date date = Date.valueOf(LocalDate.now());
        final String from = CurrencyUnit.EUR.getCode();
        final String to = CurrencyUnit.USD.getCode();
        final BigDecimal amount = BigDecimal.ONE;
        final CurrencyRate crFrom = CurrencyRate.builder()
                .value(new BigDecimal("72.6993"))
                .nominal(1)
                .build();
        final CurrencyRate crTo = CurrencyRate.builder()
                .value(new BigDecimal("64.4326"))
                .nominal(1)
                .build();
        when(currRateService.getCurrByDate(date, from)).thenReturn(crFrom);
        when(currRateService.getCurrByDate(date, to)).thenReturn(crTo);
        currencyService = spy(currencyService);
        doReturn(date).when(currencyService).getActualCurrencyRateDate(any(), eq(from), eq(to), eq(false));

        final BigDecimal result = currencyService.exchange(from, to, amount);

        assertThat(result, is(amount.multiply(new BigDecimal("1.1283"))));
        verify(currencyService).getActualCurrencyRateDate(any(), eq(from), eq(to), eq(false));
        verify(currRateService).getCurrByDate(date, from);
        verify(currRateService).getCurrByDate(date, to);
    }

    @Test
    public void exchangeOneEURToRUB() {
        final Date date = Date.valueOf(LocalDate.now());
        final String from = CurrencyUnit.EUR.getCode();
        final String to = kappersProperties.getRubCurrencyCode();
        final BigDecimal amount = BigDecimal.ONE;
        final CurrencyRate crFrom = CurrencyRate.builder()
                .value(new BigDecimal("72.6993"))
                .nominal(1)
                .build();
        when(currRateService.getCurrByDate(date, from)).thenReturn(crFrom);
        currencyService = spy(currencyService);
        doReturn(date).when(currencyService).getActualCurrencyRateDate(any(), eq(from), eq(to), eq(false));

        final BigDecimal result = currencyService.exchange(from, to, amount);

        assertThat(result, is(amount.multiply(crFrom.getValue())));
        verify(currencyService).getActualCurrencyRateDate(any(), eq(from), eq(to), eq(false));
        verify(currRateService).getCurrByDate(date, from);
        verify(currRateService, never()).getCurrByDate(date, to);
    }

    @Test
    public void exchangeOneRUBToUSD() {
        final Date date = Date.valueOf(LocalDate.now());
        final String from = kappersProperties.getRubCurrencyCode();
        final String to = CurrencyUnit.USD.getCode();
        final BigDecimal amount = BigDecimal.ONE;
        final CurrencyRate crTo = CurrencyRate.builder()
                .value(new BigDecimal("64.4326"))
                .nominal(1)
                .build();
        when(currRateService.getCurrByDate(date, to)).thenReturn(crTo);
        currencyService = spy(currencyService);
        doReturn(date).when(currencyService).getActualCurrencyRateDate(any(), eq(from), eq(to), eq(false));

        final BigDecimal result = currencyService.exchange(from, to, amount);

        assertThat(result, is(amount.divide(crTo.getValue(), kappersProperties.getBigDecimalRoundingMode())));
        verify(currencyService).getActualCurrencyRateDate(any(), eq(from), eq(to), eq(false));
        verify(currRateService, never()).getCurrByDate(date, from);
        verify(currRateService).getCurrByDate(date, to);
    }
}