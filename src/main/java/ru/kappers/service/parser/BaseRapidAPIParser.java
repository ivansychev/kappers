package ru.kappers.service.parser;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.springframework.core.convert.ConversionService;

/**
 * Базовый класс обобщенного Rapid API парсера
 * @param <T> тип сущности, которую предполагается парсить
 */
public abstract class BaseRapidAPIParser<T> implements RapidAPIParser<T> {
    protected final JsonParser jsonParser = new JsonParser();
    protected final Gson gson = new Gson();
    protected final ConversionService conversionService;

    public BaseRapidAPIParser(ConversionService conversionService) {
        this.conversionService = conversionService;
    }
}
