package ru.kappers.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.event.ContextRefreshedEvent;
import ru.kappers.config.KappersProperties;
import ru.kappers.service.CurrencyService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationStartListenerTest {
    @InjectMocks
    private ApplicationStartListener applicationStartListener;
    @Mock
    private CurrencyService currencyService;
    @Mock
    private KappersProperties kappersProperties;

    @Test
    public void onApplicationEvent() {
        applicationStartListener.onApplicationEvent(mock(ContextRefreshedEvent.class));

        verify(currencyService).refreshCurrencyRatesForToday();
    }
}