package ru.kappers.service;

import ru.kappers.model.*;

import java.util.List;

public interface UserService {
    User addUser(User user);
    void deleteByUserName(String userName);
    void delete(User user);
    User getByUserName(String name);
    User getByName(String name);
    User getById(int id);
    User editUser(User user);
    List<User> getAll();
    List<User> getAllByRole(String roleName);
    boolean hasRole(User user, String roleName);
    boolean hasRole(User user, int roleId);
    boolean hasRole(User user, Role role);
    Role getRole(User user);
    History getHistory(User user);
    Stat getStat(User user);
    KapperInfo getKapperInfo(User user);
    PersonalInfo getInfo(User user);
    void transfer (User user, User kapper, double amount);
    //TODO getEventList, getActiveEvents etc... когда евенты сделаем
}
