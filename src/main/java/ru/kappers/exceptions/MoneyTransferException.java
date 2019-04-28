package ru.kappers.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoneyTransferException extends RuntimeException {
    public MoneyTransferException(Exception e) {
        super("Money transfer Exception", e);
        log.error("Money transfer Exception", e);
    }
}
