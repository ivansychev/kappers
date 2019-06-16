package ru.kappers.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoneyTransferException extends RuntimeException {

    protected static final String DEFAULT_MESSAGE = "Money transfer Exception";

    public MoneyTransferException(Exception e) {
        super(DEFAULT_MESSAGE, e);
        log.error(DEFAULT_MESSAGE, e);
    }
}
