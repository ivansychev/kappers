package ru.kappers.logic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kappers.model.Role;
import ru.kappers.service.RolesService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/dict/role")
public class RoleController {

    private final RolesService rolesService;

    @Autowired
    public RoleController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Role> getAllContacts() {
        final List<Role> rolesList = rolesService.getAll();
        return rolesList;
    }
}
