package ru.kappers.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kappers.model.User;

import java.util.List;

public interface UsersRepository extends CrudRepository<User, Integer> {
    List<User> findAll();
    User getUserByName(String name);
}
