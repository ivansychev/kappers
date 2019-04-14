package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.*;
import ru.kappers.repository.RolesRepository;
import ru.kappers.repository.UsersRepository;
import ru.kappers.service.UserService;
import ru.kappers.util.DateUtil;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository repository;
    private final RolesRepository rolesRepository;

    @Autowired
    public UserServiceImpl(UsersRepository repository, RolesRepository rolesRepository) {
        this.repository = repository;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public User addUser(User user) {
        final String userName = user.getUserName();
        User byUserId = repository.getByUserName(userName);
        if (byUserId != null) {
            log.info("Пользователь {} уже существует", userName);
            return byUserId;
        }
        if (user.getDateOfRegistration() == null) {
            user.setDateOfRegistration(DateUtil.getCurrentTime());
        }
        if (user.getRole() == null) {
            user.setRole(rolesRepository.getByName("ROLE_USER"));
        }
        return repository.save(user);
    }

    @Override
    public void deleteByUserName(String userName) {
        repository.deleteByUserName(userName);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public User getByUserName(String name) {
        return repository.getByUserName(name);
    }

    @Override
    public User getByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public User getById(int id) {
        return repository.findById(id)
                .orElse(null);
    }

    @Override
    public User editUser(User user) {
        return repository.save(user);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public List<User> getAllByRole(String roleName) {
        Role role = rolesRepository.getByName(roleName);
        return repository.getAllByRoleId(role.getId());
    }

    @Override
    public boolean hasRole(User user, String roleName) {
        return user.hasRole(roleName);
    }

    @Override
    public boolean hasRole(User user, int roleId) {
        return user.hasRole(roleId);
    }

    @Override
    public boolean hasRole(User user, Role role) {
        return user.hasRole(role.getId());
    }

    @Override
    public Role getRole(User user) {
       return user.getRole();
    }

    @Override
    public History getHistory(User user) {
        //TODO
        return null;
    }

    @Override
    public Stat getStat(User user) {
        //TODO
        return null;
    }

    @Override
    public KapperInfo getKapperInfo(User user) {
        //TODO
        return null;
    }

    @Override
    public PersonalInfo getInfo(User user) {
        //TODO
        return null;
    }
}
