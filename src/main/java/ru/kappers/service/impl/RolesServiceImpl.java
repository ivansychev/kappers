package ru.kappers.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.kappers.model.Roles;
import ru.kappers.service.RolesService;

import java.util.List;
@Log4j
@Service
public class RolesServiceImpl implements RolesService {
    @Override
    public Roles addRole(Roles role) {
        return null;
    }

    @Override
    public void delete(Roles role) {

    }

    @Override
    public Roles getByName(String name) {
        return null;
    }

    @Override
    public Roles getById(int id) {
        return null;
    }

    @Override
    public Roles editRole(Roles role) {
        return null;
    }

    @Override
    public List<Roles> getAll() {
        return null;
    }
}
