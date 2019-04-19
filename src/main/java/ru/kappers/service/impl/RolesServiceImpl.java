package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kappers.model.Role;
import ru.kappers.repository.RolesRepository;
import ru.kappers.service.RolesService;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
public class RolesServiceImpl implements RolesService {

    private final RolesRepository repository;

    @Autowired
    public RolesServiceImpl(RolesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role addRole(Role role) {
        log.debug("addRole(role: {})...", role);
        Objects.requireNonNull(role, "role is required");
        return repository.save(role);
    }

    @Override
    public void delete(Role role) {
        log.debug("delete(role: {})...", role);
        repository.delete(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getByName(String name) {
        log.debug("getByName(name: {})...", name);
        return repository.getByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public int getRoleIdByName(String roleName) {
        log.debug("getRoleIdByName(roleName: {})...", roleName);
        return getByName(roleName).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Role getById(int id) {
        log.debug("getById(id: {})...", id);
        return repository.findById(id)
                .orElse(null);
    }

    @Override
    public Role editRole(Role role) {
        log.debug("editRole(role: {})...", role);
        Objects.requireNonNull(role, "role is required");
        return repository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAll() {
        log.debug("getAll()...");
        return repository.findAll();
    }
}
