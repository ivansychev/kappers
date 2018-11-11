package ru.kappers.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kappers.model.Fixture;

public interface FixtureRepository extends CrudRepository<Fixture, Integer> {
}
