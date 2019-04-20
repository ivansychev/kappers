package ru.kappers.service;

import ru.kappers.model.Role;

import java.util.List;

/**
 * Интерфейс сервиса роли пользователя
 */
public interface RolesService {
    Role addRole(Role role);

    void delete(Role role);

    Role getByName(String roleName);

    int getRoleIdByName (String roleName);

    Role getById(int id);

    Role editRole(Role role);

    List<Role> getAll();
}
