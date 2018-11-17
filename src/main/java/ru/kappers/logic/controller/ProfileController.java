package ru.kappers.logic.controller;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.log4j.Log4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kappers.model.Fixture;
import ru.kappers.model.User;
import ru.kappers.service.FixtureService;
import ru.kappers.service.RolesService;
import ru.kappers.service.UserService;
import ru.kappers.util.DateUtil;
import ru.kappers.util.JsonUtil;

import java.time.LocalDate;
import java.util.Map;

@Log4j
@Controller
@RequestMapping(value = "/rest/profile")
public class ProfileController {

    private RolesService rolesService;
    private UserService userService;

    @Autowired
    public void setRolesService(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    /**
     * Пример REST взаимодействия
     *
     * @param id идентификатор пользователя
     * @return пользователь
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserInfoExampleRest(@PathVariable Long id) {

        // return userRepository.findById(id);

        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("user = " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        log.info("user = " + SecurityContextHolder.getContext().getAuthentication());
        log.info("getName = " + SecurityContextHolder.getContext().getAuthentication().getName());

        return userService.getByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
