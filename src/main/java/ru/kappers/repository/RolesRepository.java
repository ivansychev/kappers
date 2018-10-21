package ru.kappers.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kappers.model.Roles;

import java.util.List;

public interface RolesRepository extends CrudRepository<Roles, Integer> {
    Roles getByRoleName(String roleName);
    Roles getByRoleId(int id);
    List<Roles> findAll();
}
