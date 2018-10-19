package ru.kappers.logic.contract;

import ru.kappers.model.KapperInfo;
import ru.kappers.model.User;
import java.util.Map;

public interface IContract {
    KapperInfo initCapper(User user);
    KapperInfo getKapperInfo(User user);
    void blockTokens(User user, double amount);
    void unblockAmount(User user, double amount);
    void withdrawBlockedTokens(User user, double amount);
    Map<User, KapperInfo> getAllInfo();
}
