package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kappers.exceptions.UserNotHaveKapperRoleException;
import ru.kappers.model.KapperInfo;
import ru.kappers.model.Role;
import ru.kappers.model.User;
import ru.kappers.repository.KapperInfoRepository;
import ru.kappers.repository.UsersRepository;
import ru.kappers.service.KapperInfoService;
import ru.kappers.service.MessageTranslator;

@Slf4j
@Service
@Transactional
public class KapperInfoServiceImpl implements KapperInfoService {
    private final KapperInfoRepository kapperRepository;
    private final UsersRepository usersRepository;
    private final MessageTranslator translator;

    @Autowired
    public KapperInfoServiceImpl(KapperInfoRepository kapperRepository, UsersRepository usersRepository, MessageTranslator translator) {
        this.kapperRepository = kapperRepository;
        this.usersRepository = usersRepository;
        this.translator = translator;
    }

    @Override
    public KapperInfo initKapper(User user) {
        log.debug("initKapper(user: {})...", user);
        KapperInfo kapper = null;
        if (user.hasRole(Role.Names.KAPPER)) {
            kapper = getByUser(user);
            if (kapper == null) {
                kapper = KapperInfo.builder().user(user).build();
                if (kapper.getId()!=null)
                    kapper.setId(null);
                setInitialData(kapper);
                user.setKapperInfo(kapper);
                editKapper(kapper);
                return kapper;
            } else {
                String message = translator.byCode("user.alreadyIsKapper", user.getUserName());
                log.error(message);
                return kapper;
            }
        } else {
            String message = translator.byCode("user.isNotKapperButTriedInitialize", user.getUserName());
            log.error(message);
            throw new UserNotHaveKapperRoleException(message);
        }
    }

    @Override
    public void delete(User user) {
        kapperRepository.deleteByUser(user);
  //      usersRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
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
