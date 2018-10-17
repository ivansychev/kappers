package ru.kappers.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kappers.model.History;


public interface HistoryRepository extends CrudRepository<History, Integer> {
}
