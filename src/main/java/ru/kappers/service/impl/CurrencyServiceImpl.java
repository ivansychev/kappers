package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kappers.config.KappersProperties;
import ru.kappers.exceptions.CurrRateGettingException;
import ru.kappers.model.CurrencyRate;
import ru.kappers.service.CurrRateService;
import ru.kappers.service.CurrencyService;
import ru.kappers.service.parser.CBRFDailyCurrencyRatesParser;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Реализация сервиса валют
 */
@Slf4j
@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrRateService currRateService;
    private final CBRFDailyCurrencyRatesParser currencyRatesParser;
    private final KappersProperties kappersProperties;

    @Autowired
    public CurrencyServiceImpl(CurrRateService currRateService, CBRFDailyCurrencyRatesParser currencyRatesParser, KappersProperties kappersProperties) {
        this.currRateService = currRateService;
        this.currencyRatesParser = currencyRatesParser;
        this.kappersProperties = kappersProperties;
    }

    //TODO кстати, данный метод собирались в будущем вызывать по расписанию, это возможно будет настроено как раз в самом методе сервиса или отдельной конфигурации Spring контекста
    @Override
    public boolean refreshCurrencyRatesForToday() {
        log.debug("refreshCurrencyRatesForToday()...");
        try {
            final List<CurrencyRate> currencyRates = currencyRatesParser.parseFromCBRF();
            currencyRates.stream()
                    .filter(currencyRate -> !currRateService.isExist(currencyRate.getDate(), currencyRate.getCharCode()))
                    .forEach(currRateService::save);
            return true;
        } catch (Exception ex) {
            throw new CurrRateGettingException("Couldn't get currency rates", ex);
        }

    }

    /**
     * Обновить курсы валют на сегодня (по расписанию планировщика)
     */
    @Scheduled(cron = "${kappers.currency-rates.refresh-cron}")
    public void refreshCurrencyRatesForTodayByScheduler() {
        if (kappersProperties.getCurrencyRates().isRefreshCronEnabled()) {
            log.debug("refreshCurrencyRatesForTodayByScheduler()...");
        } else {
            log.debug("disabled call refreshCurrencyRatesForTodayByScheduler()");
            return;
        }
        // метод вызываемый планировщиком не должен выбрасывать исключения, иначе возможно не сработает по расписанию
        try {
            refreshCurrencyRatesForToday();
        } catch (Throwable t) {
            log.error("refreshCurrencyRatesForTodayByScheduler() error", t);
        }
    }

    public Date getActualCurrencyRateDate(Date date, String fromCurr, String toCurr, boolean currRatesGotToday) {
        log.debug("getActualCurrencyRateDate(date: {}, fromCurr: {}, toCurr: {}, currRatesGotToday: {})...",
                date, fromCurr, toCurr, currRatesGotToday);
        boolean todaysCurrRatesGot = currRatesGotToday;
        if (currRateService.isExist(date, fromCurr) && currRateService.isExist(date, toCurr))
            return date;
        else {
            if (!todaysCurrRatesGot){
                todaysCurrRatesGot = refreshCurrencyRatesForToday();
            }
            if (todaysCurrRatesGot) {
                if (!currRateService.isExist(date, fromCurr) || !currRateService.isExist(date, toCurr)) {
                    LocalDate localDate = date.toLocalDate().minusDays(1);
                    date = Date.valueOf(localDate);
                    getActualCurrencyRateDate(date, toCurr, fromCurr, todaysCurrRatesGot);
                }
            }
        }
        return date;
    }

    //TODO завести тесты на этот класс
    @Override
    public BigDecimal exchange(String fromCurr, String toCurr, BigDecimal amount){
        log.debug("exchange(fromCurr: {}, toCurr: {}, amount: {})...", fromCurr, toCurr, amount);
        if (fromCurr.equals(toCurr)) {
            return amount;
        }
        Date date = Date.valueOf(LocalDate.now());
        if (!currRateService.isExist(date, fromCurr) || !currRateService.isExist(date, toCurr)) {
            refreshCurrencyRatesForToday();
            date = getActualCurrencyRateDate(date, fromCurr, toCurr, false);

        }
        if (fromCurr.equals("RUB")) {
            CurrencyRate rate = currRateService.getCurrByDate(date, toCurr);
            return amount.divide(rate.getValue())
                    .multiply(BigDecimal.valueOf(rate.getNominal()));
        } else if (toCurr.equals("RUB")) {
            CurrencyRate rate = currRateService.getCurrByDate(date, fromCurr);
            return amount.multiply(rate.getValue())
                    .multiply(BigDecimal.valueOf(rate.getNominal()));
        }
        CurrencyRate from = currRateService.getCurrByDate(date, fromCurr);
        CurrencyRate to = currRateService.getCurrByDate(date, toCurr);
        BigDecimal amountInRub = amount.multiply(from.getValue())
                .multiply(BigDecimal.valueOf(from.getNominal()));
        return amountInRub.divide(to.getValue())
                .multiply(BigDecimal.valueOf(to.getNominal()));
    }

}

