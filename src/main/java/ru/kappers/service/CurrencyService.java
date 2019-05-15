package ru.kappers.service;

import ru.kappers.exceptions.CurrRateGettingException;

import java.math.BigDecimal;

/**
 * Интерфейс сервиса валют
 */
public interface CurrencyService {
    /**
     * Обновить курсы валют на сегодня
     * @return {@literal true}, если удалось обновить курсы валют, иначе выбросит CurrRateGettingException
     * @throws CurrRateGettingException если не удалось обновить курсы валют
     */
    boolean refreshCurrencyRatesForToday();

    /**
     * Конвертировать сумму из исходной валюты в целевую
     * @param fromCurr исходная валюта
     * @param toCurr целевая валюта
     * @param amount сумма в исходной валюте
     * @return сумма в целевой валюте
     */
    BigDecimal exchange(String fromCurr, String toCurr, BigDecimal amount);
}
