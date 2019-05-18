package ru.kappers.service.parser;


import java.util.List;

/**
 * Интерфейс обобщенного Rapid API парсера
 * @param <T> тип сущности, которую предполагается парсить
 */
public interface RapidAPIParser<T> {
    /**
     * Парсить лист сущностей из JSON
     * @param json строка JSON
     * @return лист сущностей
     */
    List<T> parseListFromJSON(String json);
}
