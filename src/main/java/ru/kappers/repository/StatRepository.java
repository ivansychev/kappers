package ru.kappers.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kappers.model.Stat;


public interface StatRepository extends CrudRepository<Stat, Integer> {
}
