package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.kappers.service.MessageTranslator;

import java.util.Locale;

/**
 * Реализация переводчика сообщений
 */
@Slf4j
@Service
public class MessageTranslatorImpl implements MessageTranslator {

    private final MessageSource messageSource;

    @Autowired
    public MessageTranslatorImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String byCode(String msgCode) {
        log.debug("byCode(msgCode: {})...", msgCode);
        return messageSource.getMessage(msgCode, null, getCurrentLocale());
    }

    @Override
    public String byCode(String msgCode, Object... parameters) {
        log.debug("byCode(msgCode: {}, parameters: {})...", msgCode, parameters);
        return messageSource.getMessage(msgCode, parameters, getCurrentLocale());
    }

    @Override
    public String byCode(String msgCode, Object[] parameters, Locale locale) {
        log.debug("byCode(msgCode: {}, parameters: {}, locale: {})...", msgCode, parameters, locale);
        return messageSource.getMessage(msgCode, parameters, locale);
    }

    /** Получить текущую Locale */
    public Locale getCurrentLocale() {
        log.debug("getCurrentLocale()...");
        return LocaleContextHolder.getLocale();
    }
}
