package ru.kappers.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotHaveKapperRoleException extends RuntimeException{
    public UserNotHaveKapperRoleException(Exception e) {
        super("User is not kapper, but tries to use kapper's action", e);
        log.error("User is not kapper, but tries to use kapper's action", e);
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
