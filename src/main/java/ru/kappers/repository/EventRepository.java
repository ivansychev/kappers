package ru.kappers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kappers.model.Event;
import ru.kappers.model.User;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> getByKapper(User user);
}
