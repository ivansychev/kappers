package ru.kappers.service;

import ru.kappers.model.Roles;

import java.util.List;

public interface RolesService {
    Roles addRole(Roles role);

    void delete(Roles role);

    Roles getByName(String name);

    Roles getById(int id);

    Roles editRole(Roles role);

    List<Roles> getAll();
}
