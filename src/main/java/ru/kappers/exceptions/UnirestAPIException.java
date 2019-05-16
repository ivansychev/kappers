package ru.kappers.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnirestAPIException extends RuntimeException {
    private static final String ERROR_TEXT = "Ошибка получения данных по Rapid API";
    public UnirestAPIException(String message, Exception e){
        super(message, e);
        log.error(message + " "+e.getMessage());
    }
    public UnirestAPIException(String message){
        super(message);
        log.error(message);
    }
    public UnirestAPIException(Exception e){
        super(ERROR_TEXT, e);
        log.error(ERROR_TEXT, e);
    }

}
