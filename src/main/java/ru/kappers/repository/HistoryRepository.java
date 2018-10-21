package ru.kappers.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kappers.model.History;
import ru.kappers.model.User;

import java.util.List;


public interface HistoryRepository extends CrudRepository<History, Integer> {
    List<History> getByUser(User user);
    void deleteAllByUser(User user);
    History getById(int id);
    List<History> findAll();
}
