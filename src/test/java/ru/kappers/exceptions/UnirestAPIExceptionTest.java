package ru.kappers.exceptions;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class UnirestAPIExceptionTest {

    @Test
    public void constructorWithMessageOnly() {
        final String testMessage = "test message";

        UnirestAPIException exception = new UnirestAPIException(testMessage);

        assertThat(exception.getMessage(), is(testMessage));
        assertThat(exception.getCause(), is(nullValue()));
    }

    @Test
    public void constructorWithExceptionOnly() {
        final Exception testException = new Exception();

        UnirestAPIException exception = new UnirestAPIException(testException);

        assertThat(exception.getMessage(), is(UnirestAPIException.ERROR_TEXT));
        assertThat(exception.getCause(), is(testException));
    }

    @Test
    public void constructorWithMessageAndException() {
        final String testMessage = "test message2";
        final Exception testException = new Exception();

        UnirestAPIException exception = new UnirestAPIException(testMessage, testException);

        assertThat(exception.getMessage(), is(testMessage));
        assertThat(exception.getCause(), is(testException));
    }
}