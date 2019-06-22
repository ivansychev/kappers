package ru.kappers.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EntitySaveException extends RuntimeException {
    public EntitySaveException(String message, Exception e) {
        super(message, e);
        log.error(message + " "+e.getMessage());
    }
    public EntitySaveException(String message){
        super(message);
        log.error(message);
    }
}

