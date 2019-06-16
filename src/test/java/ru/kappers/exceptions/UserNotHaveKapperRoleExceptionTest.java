package ru.kappers.exceptions;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class UserNotHaveKapperRoleExceptionTest {
    @Test
    public void constructorWithMessageOnly() {
        final String testMessage = "test message";

        UserNotHaveKapperRoleException exception = new UserNotHaveKapperRoleException(testMessage);

        assertThat(exception.getMessage(), is(testMessage));
        assertThat(exception.getCause(), is(nullValue()));
    }

    @Test
    public void constructorWithExceptionOnly() {
        final Exception testException = new Exception();

        UserNotHaveKapperRoleException exception = new UserNotHaveKapperRoleException(testException);

        assertThat(exception.getMessage(), is(UserNotHaveKapperRoleException.DEFAULT_MESSAGE));
        assertThat(exception.getCause(), is(testException));
    }

    @Test
    public void constructorWithMessageAndException() {
        final String testMessage = "test message2";
        final Exception testException = new Exception();

        UserNotHaveKapperRoleException exception = new UserNotHaveKapperRoleException(testMessage, testException);

        assertThat(exception.getMessage(), is(testMessage));
        assertThat(exception.getCause(), is(testException));
    }
}