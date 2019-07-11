package ru.kappers.service;

import ru.kappers.model.CurrencyRate;

import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс сервиса курсов валют
 */
public interface CurrRateService {
    CurrencyRate save(CurrencyRate rate);
    boolean isExist (LocalDate date, String currLiteral);
    CurrencyRate getCurrByDate (LocalDate date, String currLiteral);
    void clear ();
    CurrencyRate update (CurrencyRate rate);
    CurrencyRate getToday (String literal);
    List<CurrencyRate> getAllToday();
    List<CurrencyRate> getAllByDate(LocalDate date);
}
