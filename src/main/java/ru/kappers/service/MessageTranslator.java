package ru.kappers.service;

import java.util.Locale;

/**
 * Интерфейс переводчика сообщений
 */
public interface MessageTranslator {
    /**
     * Перевести сообщение с указанным кодом
     * @param msgCode код сообщения
     * @return текст переведенного сообщения
     */
    String byCode(String msgCode);

    /**
     * Перевести сообщение с указанным кодом и параметрами
     * @param msgCode код сообщения
     * @param parameters параметры сообщения
     * @return текст переведенного сообщения
     */
    String byCode(String msgCode, Object... parameters);

    /**
     * Перевести сообщение с указанным кодом и параметрами
     * @param msgCode код сообщения
     * @param parameters параметры сообщения
     * @param locale
     * @return текст переведенного сообщения
     */
    String byCode(String msgCode, Object[] parameters, Locale locale);
}
