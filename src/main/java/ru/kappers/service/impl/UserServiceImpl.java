package ru.kappers.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.*;
import ru.kappers.repository.RolesRepository;
import ru.kappers.repository.UsersRepository;
import ru.kappers.service.UserService;
import ru.kappers.util.DateUtil;

import java.sql.Timestamp;
import java.util.List;
@Log4j
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
        User byUserId = repository.getByUserName(user.getUserName());
        if (byUserId == null) {
            if (user.getDateOfRegistration() == null) {
                user.setDateOfRegistration(DateUtil.getCurrentTime());
            }
            if (user.getIsblocked() == null) {
                user.setIsblocked(false);
            }
            if (user.getRoleId() == null) {
                user.setRoleId(Roles.RoleType.ROLE_USER.getId());
            }
            repository.save(user);
        } else{
            log.info("Пользователь "+user.getUserName()+" уже существует");
        }
        return repository.getUserByName(user.getName());
    }

    @Override
    public void delete(User user) {
        User byUserId = repository.getByUserId(user.getUserId());
        if (byUserId != null)
            repository.delete(user);
    }

    @Override
    public User getByUserName(String name) {
        return repository.getByUserName(name);
    }

    @Override
    public User getByName(String name) {
        return repository.getUserByName(name);
    }

    @Override
    public User getById(int id) {
        return repository.getByUserId(id);
    }

    @Override
    public User editUser(User user) {
        User toRecord = repository.getByUserId(user.getUserId());
        toRecord.setCurrency(user.getCurrency());
        toRecord.setDateOfBirth(user.getDateOfBirth());
        toRecord.setDateOfRegistration(user.getDateOfRegistration());
        toRecord.setEmail(user.getEmail());
        toRecord.setIsblocked(user.getIsblocked());
        toRecord.setLang(user.getLang());
        toRecord.setName(user.getName());
        toRecord.setPassword(user.getPassword());
        toRecord.setRoleId(user.getRoleId());
        toRecord.setUserName(user.getUserName());
        repository.save(toRecord);
        return toRecord;
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public List<User> getAllByRole(String roleName) {
        Roles role = rolesRepository.getByRoleName(Enum.valueOf(Roles.RoleType.class, roleName));
        return repository.getAllByRoleId(role.getRoleId());
    }

    @Override
    public boolean hasRole(User user, String roleName) {
        return user.getRoleId() == rolesRepository.getByRoleName(Enum.valueOf(Roles.RoleType.class, roleName)).getRoleId();
    }

    @Override
    public boolean hasRole(User user, int roleId) {
        return user.getRoleId() == roleId;
    }

    @Override
    public boolean hasRole(User user, Roles role) {
        return user.getRoleId() == role.getRoleId();
    }

    @Override
    public Roles getRole(User user) {
  //      return rolesRepository.getByRoleId(user.getRoleId());
        return null;
        //TODO разобраться почему не срабатывает
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
