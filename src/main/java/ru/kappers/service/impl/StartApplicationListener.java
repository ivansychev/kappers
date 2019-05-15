package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import ru.kappers.service.CurrencyService;

/**
 * Слушатель старта приложения (срабатывает после завершения подъема Spring контекста)
 */
@Slf4j
@Service
public class StartApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    private final CurrencyService currencyService;

    @Autowired
    public StartApplicationListener(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("onApplicationEvent(event: {})...", event);
        log.info("Spring контекст обновился. Завершен подъём приложения");
        log.info("Обновление курсов валют...");
        currencyService.refreshCurrencyRatesForToday();
        log.info("Обновление курсов валют завершено");
    }
}
