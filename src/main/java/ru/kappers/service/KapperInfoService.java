package ru.kappers.service;

import ru.kappers.model.KapperInfo;
import ru.kappers.model.User;

import java.util.List;

public interface KapperInfoService {
    KapperInfo initKapper(User user);
    void delete(User user);
    KapperInfo getByUser(User user);
    KapperInfo editKapper(KapperInfo kapperInfo);
}
