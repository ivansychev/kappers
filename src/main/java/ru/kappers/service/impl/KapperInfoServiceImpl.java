package ru.kappers.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.KapperInfo;
import ru.kappers.model.User;
import ru.kappers.repository.KapperInfoRepository;
import ru.kappers.service.KapperInfoService;

@Log4j
@Service
public class KapperInfoServiceImpl implements KapperInfoService {
    private final KapperInfoRepository kapperRepository;

    @Autowired
    public KapperInfoServiceImpl(KapperInfoRepository kapperRepository) {

        this.kapperRepository = kapperRepository;
    }

    @Override
    public KapperInfo initKapper(User user) {
        KapperInfo kapper = null;
        if (user.hasRole("ROLE_KAPPER")) {
            kapper = getByUser(user);
            if (kapper == null) {
                kapper = KapperInfo.builder().user(user).build();
                setInitialData(kapper);
                return kapper;
            } else {
                String message = "The user " + user.getUserName() + " has already been initialized as Kapper.";
                log.error(message);
                throw new IllegalArgumentException(message);
            }
        } else {
            String message = "The user " + user.getUserName() + " doesn't have KAPPER role, but it is being tried to initialize as KAPPER.";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public void delete(User user) {
        kapperRepository.deleteByUser(user);
    }

    @Override
    public KapperInfo getByUser(User user) {
        return kapperRepository.getKapperInfoByUser(user);
    }

    @Override
    public KapperInfo editKapper(KapperInfo kapperInfo) {
        return kapperRepository.save(kapperInfo);
    }

    private void setInitialData(KapperInfo k) {
        k.setTokens(500);
        k.setBets(0);
        k.setBlockedTokens(0);
        k.setFailBets(0);
        k.setSuccessBets(0);
    }
}
