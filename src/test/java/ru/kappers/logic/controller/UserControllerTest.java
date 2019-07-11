package ru.kappers.logic.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kappers.model.Role;
import ru.kappers.model.User;
import ru.kappers.model.dto.RoleDto;
import ru.kappers.service.MessageTranslator;
import ru.kappers.service.UserService;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MessageTranslator messageTranslator;

    @Test
    public void loginMustReturnTrueIfUserPasswordMatches() {
        final String username = "TestName";
        final String password = "Test123";
        final User user = User.builder()
                .userName(username)
                .password(password)
                .build();
        final User dbUser = new User();
        BeanUtils.copyProperties(user, dbUser);
        when(userService.getByUserName(username)).thenReturn(dbUser);
        when(passwordEncoder.matches(eq(password), anyString())).thenReturn(true);
        userController = spy(userController);

        final boolean result = userController.login(user);

        assertThat(result, is(true));
        verify(userController).getCurrentAuthentication();
        verify(userService).getByUserName(username);
        verify(passwordEncoder).matches(eq(password), anyString());
    }

    @Test
    public void loginMustReturnFalseIfUserPasswordDoNotMatches() {
        final String username = "TestName";
        final String password = "Test123";
        final User user = User.builder()
                .userName(username)
                .password(password)
                .build();
        final User dbUser = new User();
        BeanUtils.copyProperties(user, dbUser);
        when(userService.getByUserName(username)).thenReturn(dbUser);
        when(passwordEncoder.matches(eq(password), anyString())).thenReturn(false);
        userController = spy(userController);

        final boolean result = userController.login(user);

        assertThat(result, is(false));
        verify(userController).getCurrentAuthentication();
        verify(userService).getByUserName(username);
        verify(passwordEncoder).matches(eq(password), anyString());
    }

    @Test
    public void loginMustReturnFalseIfUserServiceNotFoundUser() {
        final String username = "TestName";
        final String password = "Test123";
        final User user = User.builder()
                .userName(username)
                .password(password)
                .build();
        final User dbUser = new User();
        BeanUtils.copyProperties(user, dbUser);
        userController = spy(userController);

        final boolean result = userController.login(user);

        assertThat(result, is(false));
        verify(userController).getCurrentAuthentication();
        verify(userService).getByUserName(username);
        verify(passwordEncoder, never()).matches(eq(password), anyString());
    }

    @Test(expected = NullPointerException.class)
    public void loginMustThrowExceptionIfUserIsNull() {
        userController.login(null);
    }

    @Test
    public void user() {
        final UserDetails userDetails = mock(UserDetails.class);
        final Authentication auth = new TestingAuthenticationToken(userDetails, null, Role.Names.KAPPER);

        MockHttpServletRequest request = new MockHttpServletRequest();
        String headerValue = "Basic:" + Base64.getEncoder().encodeToString(auth.toString().getBytes());
        final String headerName = "Authorization";
        request.addHeader(headerName, headerValue);
        request = spy(request);

        final Principal principal = userController.user(request);

        assertNotNull(principal);
//        assertThat(principal, is(userDetails));
        verify(request).getHeader(headerName);
    }

    @Test(expected = NullPointerException.class)
    public void userMustThrowExceptionIfRequestIsNull() {
        userController.user(null);
    }

    @Test
    public void getCurrentUserRole() {
        userController = spy(userController);
        doReturn(Role.Names.KAPPER).when(userController).getCurrentFirstRole();

        final RoleDto role = userController.getCurrentUserRole();

        assertNotNull(role);
        assertThat(role.getRole(), is(Role.Names.KAPPER));
        verify(userController).getCurrentFirstRole();
    }

    @Test
    public void logoutSuccess() {
        userController = spy(userController);
        doReturn(Role.Names.USER).when(userController).getCurrentFirstRole();

        final RoleDto role = userController.logoutSuccess();

        assertNotNull(role);
        assertThat(role.getRole(), is(Role.Names.USER));
        verify(userController).getCurrentFirstRole();
    }

    @Test
    public void logout() {
        userController = spy(userController);

        final RoleDto role = userController.logout();

        assertNotNull(role);
        assertThat(role.getRole(), is(Role.Names.ANONYMOUS));
        verify(userController, never()).getCurrentFirstRole();
    }

    @Test
    public void create() {
        final PasswordEncoder encoder = spy(new BCryptPasswordEncoder(4));
        userController = new UserController(userService, encoder, messageTranslator);
        final String testPassword = "testPassword";
        final User user = User.builder()
                .dateOfBirth(LocalDateTime.now().minusYears(20))
                .dateOfRegistration(LocalDateTime.now().minusMinutes(20))
                .password(testPassword)
                .build();
        final User userCopy = new User();
        BeanUtils.copyProperties(user, userCopy);
        when(userService.addUser(user)).thenReturn(user);

        assertThat(user, is(userCopy));

        final User resultUser = userController.create(user);

        assertNotNull(resultUser);
        assertThat(resultUser == user, is(true));
        assertThat(resultUser.getDateOfBirth(), is(userCopy.getDateOfBirth()));
        assertThat(resultUser.getPassword(), is(not(testPassword)));
        assertThat(resultUser.getDateOfRegistration(), is(notNullValue()));
        assertThat(resultUser.getDateOfRegistration(), is(not(userCopy.getDateOfRegistration())));
        verify(encoder).encode(testPassword);
        verify(userService).addUser(user);
    }

    @Test(expected = NullPointerException.class)
    public void createMustThrowExceptionIfUserIsNull() {
        userController.create(null);
    }

    @Test
    public void getCurrentFirstRole() {
        userController = spy(userController);
        final Authentication auth = new TestingAuthenticationToken(mock(User.class), null, Role.Names.ADMIN);
        doReturn(auth).when(userController).getCurrentAuthentication();

        final String firstRole = userController.getCurrentFirstRole();

        assertThat(firstRole, is(Role.Names.ADMIN));
        verify(userController).getCurrentAuthentication();
    }

    @Test(expected = NoSuchElementException.class)
    public void getCurrentFirstRoleMustThrowExceptionIfAuthenticationAuthoritiesIsNull() {
        userController = spy(userController);
        doReturn(mock(Authentication.class)).when(userController).getCurrentAuthentication();

        try {
            userController.getCurrentFirstRole();
        } catch (Throwable t) {
            verify(userController).getCurrentAuthentication();
            throw t;
        }
    }

    @Test
    public void getCurrentAuthenticatedUser() {
        userController = spy(userController);
        final UserDetails userDetails = mock(UserDetails.class);
        final String testUserName = "testUserName";
        when(userDetails.getUsername()).thenReturn(testUserName);
        final Authentication auth = new TestingAuthenticationToken(userDetails, null, Role.Names.KAPPER);
        doReturn(auth).when(userController).getCurrentAuthentication();
        final User expectedUser = mock(User.class);
        when(userService.getByUserName(testUserName)).thenReturn(expectedUser);

        final User user = userController.getCurrentAuthenticatedUser();

        assertNotNull(user);
        assertThat(user, is(expectedUser));
        verify(userController).getCurrentAuthentication();
        verify(userService).getByUserName(testUserName);
    }

    @Test(expected = IllegalStateException.class)
    public void getCurrentAuthenticatedUserMustThrowExceptionIfUserNotFoundInDatabase() {
        userController = spy(userController);
        final UserDetails userDetails = mock(UserDetails.class);
        final String testUserName = "testUserName";
        when(userDetails.getUsername()).thenReturn(testUserName);
        final Authentication auth = new TestingAuthenticationToken(userDetails, null, Role.Names.KAPPER);
        doReturn(auth).when(userController).getCurrentAuthentication();

        try {
            userController.getCurrentAuthenticatedUser();
        } catch (Throwable t) {
            verify(userController).getCurrentAuthentication();
            verify(userService).getByUserName(testUserName);
            throw t;
        }
    }
}