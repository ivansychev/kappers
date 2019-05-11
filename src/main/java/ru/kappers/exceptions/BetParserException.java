package ru.kappers.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BetParserException extends RuntimeException{
    public BetParserException(String message, Exception e){
        super(message, e);
        log.error(message + " "+e.getMessage());
    }
    public BetParserException(String message){
        super(message);
        log.error(message);
    }

}

