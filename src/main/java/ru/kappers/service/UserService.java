package ru.kappers.service;

import ru.kappers.model.*;

import java.util.List;

public interface UserService {
    User addUser(User user);
    void delete(User id);
    User getByUserName(String name);
    User getByName(String name);
    User getById(int id);
    User editUser(User user);
    List<User> getAll();
    List<User> getAllByRole(String roleName);
    boolean hasRole(User user, String roleName);
    boolean hasRole(User user, int roleId);
    boolean hasRole(User user, Roles role);
    Roles getRole(User user);
    History getHistory(User user);
    Stat getStat(User user);
    KapperInfo getKapperInfo(User user);
    PersonalInfo getInfo(User user);
    //TODO getEventList, getActiveEvents etc... когда евенты сделаем
}
