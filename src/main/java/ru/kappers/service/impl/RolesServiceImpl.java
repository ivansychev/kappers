package ru.kappers.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.Role;
import ru.kappers.repository.RolesRepository;
import ru.kappers.service.RolesService;

import java.util.List;
import java.util.Objects;

@Log4j
@Service
public class RolesServiceImpl implements RolesService {

    private final RolesRepository repository;

    @Autowired
    public RolesServiceImpl(RolesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role addRole(Role role) {
        return repository.save(role);
    }

    @Override
    public void delete(Role role) {
        repository.delete(role);
    }

    @Override
    public Role getByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public int getRoleIdByName(String roleName) {
        return getByName(roleName).getId();
    }

    @Override
    public Role getById(int id) {
        return repository.findById(id)
                .orElse(null);
    }

    @Override
    public Role editRole(Role role) {
        Role toRecord = repository.findById(role.getId())
                .orElse(null);
        Objects.requireNonNull(toRecord).setName(role.getName());
        toRecord.setEnabled(role.isEnabled());
        toRecord.setUsers(role.getUsers());
        Role saved = repository.save(toRecord);
        return saved;
    }

    @Override
    public List<Role> getAll() {
        return repository.findAll();
    }
}
