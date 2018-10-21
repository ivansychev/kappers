package ru.kappers.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.Roles;
import ru.kappers.repository.RolesRepository;
import ru.kappers.service.RolesService;

import java.util.List;

@Log4j
@Service
public class RolesServiceImpl implements RolesService {
    private final RolesRepository repository;

    @Autowired
    public RolesServiceImpl(RolesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Roles addRole(Roles role) {
        return repository.save(role);
    }

    @Override
    public void delete(Roles role) {
        repository.delete(role);
    }

    @Override
    public Roles getByName(String name) {
        return repository.getByRoleName(name);
    }

    @Override
    public int getRoleIdByName(String roleName) {
        return getByName(roleName).getRoleId();
    }

    @Override
    public Roles getById(int id) {
        return repository.getByRoleId(id);
    }

    @Override
    public Roles editRole(Roles role) {
        return null; //TODO
    }

    @Override
    public List<Roles> getAll() {
        return repository.findAll();
    }
}
