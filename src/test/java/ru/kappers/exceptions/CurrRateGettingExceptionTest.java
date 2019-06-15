package ru.kappers.exceptions;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class CurrRateGettingExceptionTest {

    @Test
    public void constructorWithMessageOnly() {
        final String testMessage = "test message";

        CurrRateGettingException exception = new CurrRateGettingException(testMessage);

        assertThat(exception.getMessage(), is(testMessage));
        assertThat(exception.getCause(), is(nullValue()));
    }

    @Test
    public void constructorWithMessageAndException() {
        final String testMessage = "test message2";
        final Exception testException = new Exception();

        CurrRateGettingException exception = new CurrRateGettingException(testMessage, testException);

        assertThat(exception.getMessage(), is(testMessage));
        assertThat(exception.getCause(), is(testException));
    }
}