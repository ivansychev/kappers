package ru.kappers.logic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kappers.model.Role;
import ru.kappers.model.User;
import ru.kappers.model.dto.RoleDto;
import ru.kappers.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;
import java.util.Collection;

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
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
}
