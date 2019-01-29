package ru.kappers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kappers.model.User;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, Integer> {
    User getByName(String name);
    List<User> getAllByRoleId(int roleId);
    User getByUserName(String userName);
    void deleteByUserName(String userName);
    void delete(User userName);
}
