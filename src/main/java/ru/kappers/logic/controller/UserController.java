package ru.kappers.logic.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kappers.model.User;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;

@Controller
public class UserController {

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean login(@RequestBody User user) {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("user = " + user.toString());
        return
                user.getUserName().equals("user") && user.getPassword().equals("password");
    }

    @ResponseBody
    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring("Basic".length()).trim();
        return () ->  new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }
}
