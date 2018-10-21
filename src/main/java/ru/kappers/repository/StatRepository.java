package ru.kappers.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.kappers.model.Stat;
import ru.kappers.model.User;
import ru.kappers.model.utilmodel.IssueType;

import java.util.List;


public interface StatRepository extends CrudRepository<Stat, Integer> {
        void deleteAllByUser(User user);
        List<Stat> getAllByUser(User user);
        List<Stat> getAllByIssueType(IssueType issueType);
        List<Stat> findAll();
}
