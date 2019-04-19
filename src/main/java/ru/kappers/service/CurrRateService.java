package ru.kappers.service;

import ru.kappers.model.CurrencyRate;
import ru.kappers.repository.CurrRateRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс сервиса курсов валют
 */
public interface CurrRateService {
    CurrencyRate save(CurrencyRate rate);
    boolean isExist (Date date, String currLiteral);
    CurrencyRate getCurrByDate (Date date, String currLiteral);
    void clear ();
    CurrencyRate update (CurrencyRate rate);
    CurrencyRate getToday (String literal);
    List<CurrencyRate> getAllToday();
    List<CurrencyRate> getAllByDate(Date date);
}
