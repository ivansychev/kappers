package ru.kappers.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurrRateGettingException extends RuntimeException{
    public CurrRateGettingException(String message, Exception e){
        super(message, e);
        log.error(message + " "+e.getMessage());
    }
    public CurrRateGettingException(String message){
        super(message);
        log.error(message);
    }

}
