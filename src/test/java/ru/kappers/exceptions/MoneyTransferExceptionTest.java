package ru.kappers.exceptions;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MoneyTransferExceptionTest {

    @Test
    public void constructorWithExceptionOnly() {
        final Exception testException = new Exception();

        MoneyTransferException exception = new MoneyTransferException(testException);

        assertThat(exception.getMessage(), is(MoneyTransferException.DEFAULT_MESSAGE));
        assertThat(exception.getCause(), is(testException));
    }
}