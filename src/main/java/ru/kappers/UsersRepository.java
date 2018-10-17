package ru.kappers;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsersRepository extends CrudRepository<User, Integer> {
    List<User> findAll();
    User getUserByName(String name);
}
