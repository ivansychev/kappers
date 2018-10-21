package ru.kappers.service;

import ru.kappers.model.Stat;
import ru.kappers.model.User;
import ru.kappers.model.utilmodel.IssueType;

import java.util.List;

public interface StatService {
    Stat addRecord(Stat stat);
    void deleteRecord(Stat id);
    void clearUserStat(User user);
    List<Stat> getUserStat(User user);
    List<Stat> getAllByIssueType(IssueType type);
    Stat updateStatRecord (Stat stat);
    List<Stat> getAll();
}
