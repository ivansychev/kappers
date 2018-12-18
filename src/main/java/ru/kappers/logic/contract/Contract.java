package ru.kappers.logic.contract;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.KapperInfo;
import ru.kappers.model.User;
import ru.kappers.repository.KapperInfoRepository;
import ru.kappers.repository.UsersRepository;
import ru.kappers.service.KapperInfoService;
import ru.kappers.service.UserService;

import java.util.Map;

/**
 * Created by Shoma on 29.09.2018.
 */

@Log4j
public class Contract implements IContract {

    private UserService userService;
    private KapperInfoService kapperService;

    @Autowired
    public Contract(UserService userService, KapperInfoService kapperService) {
        this.userService = userService;
        this.kapperService = kapperService;
    }

    @Override
    public KapperInfo initCapper(User user) {
        if (user != null) {
            kapperService.initKapper(user);
        }
        return kapperService.getByUser(user);
    }

    @Override
    public KapperInfo getKapperInfo(User user) {
        return kapperService.getByUser(user);
    }

    @Override
    public synchronized void blockTokens(User user, Integer amount) {
        KapperInfo kapperInfo = getKapperInfo(user);
        int blocked = kapperInfo.getBlockedTokens();
        blocked+=amount;
        kapperInfo.setBlockedTokens(blocked);
    }

    @Override
    public Map<User, KapperInfo> getAllInfo() {
        return null;
    }

    @Override
    public synchronized void unblockAmount(User user, Integer amount) {
        KapperInfo kapperInfo = getKapperInfo(user);
        int blocked = kapperInfo.getBlockedTokens();
        if (blocked<=amount){
        blocked-=amount;
        } else {
            String message = "У каппера "+user.getUserName()+" нет заблокированных токенов в количестве "+amount;
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        kapperInfo.setBlockedTokens(blocked);
        kapperInfo.setTokens(kapperInfo.getTokens() + amount);
    }

    @Override
    public void withdrawBlockedTokens(User user, Integer amount) {

    }
}
