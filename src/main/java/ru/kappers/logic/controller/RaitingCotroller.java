package ru.kappers.logic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kappers.model.Fixture;
import ru.kappers.model.User;
import ru.kappers.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Controller
@RequestMapping(value = "/rest/raiting")
public class RaitingCotroller {
    @Autowired
    private UserService service;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<User> getLastWeek() {
        Set<User> kappers = new TreeSet<>(service.getAllByRole("ROLE_KAPPER"));
        return kappers;
    }
}
