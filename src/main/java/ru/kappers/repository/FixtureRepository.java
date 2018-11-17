package ru.kappers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.kappers.model.Fixture;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface FixtureRepository extends JpaRepository<Fixture, Integer> {
    @Query(
            value = "delete FROM fixtures WHERE fixture_id = ?1",
            nativeQuery = true)
    void deleteById(int fixtureId);
    @Query(
            value = "select * FROM fixtures WHERE event_date > ?1 and event_date < ?2",
            nativeQuery = true)
    List<Fixture> getFixturesByPeriod(Timestamp from, Timestamp to);

    @Query(
            value = "select * FROM fixtures WHERE event_date > ?1 and event_date < ?2 and status = ?3",
            nativeQuery = true)
    List<Fixture> getFixturesByPeriod(Timestamp from, Timestamp to, String filter);
}
