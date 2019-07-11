package ru.kappers.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kappers.model.Fixture;
import ru.kappers.model.Fixture.Status;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для спортивных событий
 */
public interface FixtureRepository extends JpaRepository<Fixture, Integer> {
    @Query(value = "select f FROM Fixture f WHERE f.eventDate >= :from and f.eventDate <= :to")
    List<Fixture> getFixturesByPeriod(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query(value = "select f FROM Fixture f WHERE f.eventDate >= :from and f.eventDate <= :to")
    Page<Fixture> getFixturesByPeriod(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to, Pageable pageable);

    @Query(value = "select f FROM Fixture f WHERE f.eventDate >= :from and f.eventDate <= :to and f.status = :filter")
    List<Fixture> getFixturesByPeriod(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to, @Param("filter") Status filter);

    @Query(value = "select f FROM Fixture f WHERE f.eventDate >= :from and f.eventDate <= :to and f.status = :filter")
    Page<Fixture> getFixturesByPeriod(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to, @Param("filter") Status filter,
                                      Pageable pageable);
}
