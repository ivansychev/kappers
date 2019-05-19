package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import ru.kappers.config.KappersProperties;
import ru.kappers.service.CurrencyService;

/**
 * Слушатель старта приложения (срабатывает после завершения подъема Spring контекста)
 */
@Slf4j
@Service
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {
    private final CurrencyService currencyService;
    private final KappersProperties kappersProperties;

    @Autowired
    public ApplicationStartListener(CurrencyService currencyService, KappersProperties kappersProperties) {
        this.currencyService = currencyService;
        this.kappersProperties = kappersProperties;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("onApplicationEvent(event: {})...", event);
        log.info("Spring контекст обновился. Завершен подъём приложения");
        log.info("Свойства приложения KappersProperties: {}", kappersProperties);
        log.info("Обновление курсов валют...");
        currencyService.refreshCurrencyRatesForToday();
        log.info("Обновление курсов валют завершено");
    }
}
