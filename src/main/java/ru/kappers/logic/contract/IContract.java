package ru.kappers.logic.contract;

import ru.kappers.model.KapperInfo;
import ru.kappers.model.User;
import java.util.Map;

public interface IContract {
    KapperInfo initCapper(User user);
    KapperInfo getKapperInfo(User user);
    void blockTokens(User user, Integer amount);
    void unblockAmount(User user, Integer amount);
    void withdrawBlockedTokens(User user, Integer amount);
    Map<User, KapperInfo> getAllInfo();
}
