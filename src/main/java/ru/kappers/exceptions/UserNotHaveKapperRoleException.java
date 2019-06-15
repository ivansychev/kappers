package ru.kappers.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotHaveKapperRoleException extends RuntimeException{

    protected static final String DEFAULT_MESSAGE = "User is not kapper, but tries to use kapper's action";

    public UserNotHaveKapperRoleException(Exception e) {
        super(DEFAULT_MESSAGE, e);
        log.error(DEFAULT_MESSAGE, e);
    }

    public UserNotHaveKapperRoleException(String message, Exception e){
        super(message, e);
        log.error(message + " "+e.getMessage());
    }

    public UserNotHaveKapperRoleException(String message){
        super(message);
        log.error(message);
    }
}
