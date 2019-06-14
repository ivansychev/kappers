package ru.kappers.exceptions;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BetParserExceptionTest {

    @Test
    public void constructorWithMessageOnly() {
        final String testMessage = "test message";

        BetParserException exception = new BetParserException(testMessage);

        assertThat(exception.getMessage(), is(testMessage));
    }

    @Test
    public void constructorWithMessageAndException() {
        final String testMessage = "test message2";
        final Exception testException = new Exception();

        BetParserException exception = new BetParserException(testMessage, testException);

        assertThat(exception.getMessage(), is(testMessage));
        assertThat(exception.getCause(), is(testException));
    }
}