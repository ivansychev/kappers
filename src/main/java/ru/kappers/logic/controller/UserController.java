package ru.kappers.logic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kappers.model.User;
import ru.kappers.model.dto.RoleDto;
import ru.kappers.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Base64;

@Controller
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    private final PasswordEncoder passwordEncoder;

    /**
     * Кастомная авторизация.
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean login(@RequestBody User user) {
        SecurityContextHolder.getContext().getAuthentication();
        System.out.println("user = " + user.toString());

        String username = user.getUserName();
        String password = user.getPassword();

        user = userService.getByUserName(username);

        System.out.println("user2 = " + user.toString());

        boolean result = false;

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            result = true;
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring("Basic".length()).trim();
        return () ->  new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];
    }

    @RequestMapping(value = "/sign-in/get-authority", method = RequestMethod.GET)
    @ResponseBody
    public RoleDto getCurrentUserRole() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
        return new RoleDto(role);
    }

    @RequestMapping(value = "/sign-in/logout-success", method = RequestMethod.GET)
    @ResponseBody
    public RoleDto logoutSuccess() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
        return new RoleDto(role);
    }

    @RequestMapping(value = "/sign-in/perform-logout", method = RequestMethod.GET)
    @ResponseBody
    public RoleDto logout() {
        return new RoleDto("ROLE_ANONYMOUS");
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User create(@RequestBody User user) {
        user.setDateOfBirth(new Timestamp(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return user;
    }

    @ResponseBody
    @RequestMapping(value = "/rest/user/get-current-authorized", method = RequestMethod.GET)
    public User getCurrentAuthUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("username = " + username);
        User user = userService.getByUserName(username);
        if (user == null) {
            throw new IllegalStateException("Authenticated user not found in the database.");
        }
        return user;
    }
}
