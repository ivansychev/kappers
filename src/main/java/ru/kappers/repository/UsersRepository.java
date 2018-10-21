package ru.kappers.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.kappers.model.User;

import java.util.List;

public interface UsersRepository extends CrudRepository<User, Integer> {
    List<User> findAll();
    User getUserByName(String name);
    User getByUserId(int id);
    List<User> getAllByRoleId(int roleId);
    User getByUserName(String userName);
    void deleteByUserName(String userName);
    void delete(User user);
}
