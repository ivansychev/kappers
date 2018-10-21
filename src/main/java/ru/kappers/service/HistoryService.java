package ru.kappers.service;

import ru.kappers.model.History;
import ru.kappers.model.User;

import java.util.List;

public interface HistoryService {
    History addHistoryRecord(History record);
    void delete(History record);
    void clearHistory(User user);
    History getById(int id);
    List<History> getUsersHistory(User user);
    //TODO добавить дату, сделать извлечение истории по диапазонам дат
    History editHistory(History record);
    List<History> getAll();
}
