package ru.kappers.service.impl;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.kappers.exceptions.MoneyTransferException;
import ru.kappers.model.*;
import ru.kappers.repository.UsersRepository;
import ru.kappers.service.CurrRateService;
import ru.kappers.service.KapperInfoService;
import ru.kappers.service.RolesService;
import ru.kappers.service.UserService;
import ru.kappers.util.CurrencyUtil;
import ru.kappers.util.DateTimeUtil;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Реализация сервиса пользователя
 */
@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UsersRepository repository;
    private final RolesService rolesService;
    private final CurrRateService currService;
    private final KapperInfoService kapperInfoService;

    @Autowired
    public UserServiceImpl(UsersRepository repository, RolesService rolesService, CurrRateService currService, KapperInfoService kapperInfoService) {
        this.repository = repository;
        this.rolesService = rolesService;
        this.currService = currService;
        this.kapperInfoService = kapperInfoService;
    }

    @Override
    public User addUser(User user) {
        log.debug("addUser(user: {})...", user);
        Role role = user.getRole();
        if (role != null && role.getName() == null) {
            role.setName(rolesService.getById(role.getId()).getName());
        }
        user.setRole(role);
        final String userName = user.getUserName();
        User userByUserName = repository.getByUserName(userName);
        if (userByUserName != null) {
            log.info("Пользователь {} уже существует", userName);
            saveKapperInfo(userByUserName);
            return userByUserName;
        }
        if (user.getDateOfRegistration() == null) {
            user.setDateOfRegistration(DateTimeUtil.getCurrentTime());
        }
        if (user.getRole() == null) {
            user.setRole(rolesService.getByName("ROLE_USER"));
        }
        User savedUser = repository.save(user);
        saveKapperInfo(savedUser);
        return savedUser;
    }

    private User saveKapperInfo(User user) {
        if (user.getRole().equals(rolesService.getByName("ROLE_KAPPER"))) {
            kapperInfoService.initKapper(user);
            return repository.getOne(user.getId());
        }
        return user;
    }

    @Override
    public void deleteByUserName(String userName) {
        log.debug("deleteByUserName(userName: {})...", userName);
        User user = getByUserName(userName);
        if (user.hasRole("ROLE_KAPPER")) {
            kapperInfoService.delete(user);
        } else {
            repository.deleteByUserName(userName);
        }
    }

    @Override
    public void delete(User user) {
        log.debug("delete(user: {})...", user);
        if (user.hasRole("ROLE_KAPPER")) {
            kapperInfoService.delete(user);
        } else {
            repository.delete(user);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUserName(String name) {
        log.debug("getByUserName(name: {})...", name);
        User byUserName = repository.getByUserName(name);
//        byUserName.getEvents().iterator();
        return byUserName;
    }

    @Override
    @Transactional(readOnly = true)
    public User getByName(String name) {
        log.debug("getByName(name: {})...", name);
        return repository.getByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(int id) {
        log.debug("getById(id: {})...", id);
        return repository.findById(id)
                .orElse(null);
    }

    @Override
    public User editUser(User user) {
        log.debug("editUser(user: {})...", user);
        return repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        log.debug("getAll()...");
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllByRole(String roleName) {
        log.debug("getAllByRole(roleName: {})...", roleName);
        Role role = rolesService.getByName(roleName);
        return repository.getAllByRoleId(role.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasRole(User user, String roleName) {
        log.debug("hasRole(user: {}, roleName: {})...", user, roleName);
        return user.hasRole(roleName);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasRole(User user, int roleId) {
        log.debug("hasRole(user: {}, roleId: {})...", user, roleId);
        return user.hasRole(roleId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasRole(User user, Role role) {
        log.debug("hasRole(user: {}, role: {})...", user, role);
        return user.hasRole(role.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRole(User user) {
        log.debug("getRole(user: {})...", user);
        return user.getRole();
    }

    @Override
    @Transactional(readOnly = true)
    public History getHistory(User user) {
        log.debug("getHistory(user: {})...", user);
        //TODO
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Stat getStat(User user) {
        log.debug("getStat(user: {})...", user);
        //TODO
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public KapperInfo getKapperInfo(User user) {
        log.debug("getKapperInfo(user: {})...", user);
        if (user.hasRole("ROLE_KAPPER")) {
            return user.getKapperInfo();
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonalInfo getInfo(User user) {
        log.debug("getInfo(user: {})...", user);
        //TODO
        return null;
    }

    //TODO написать unit-тесты для transfer и exchange
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public synchronized void transfer(User user, User kapper, BigDecimal amount){
        log.debug("transfer(user: {}, kapper: {}, amount: {})...", user, kapper, amount);
        Preconditions.checkArgument(user.hasRole("ROLE_USER"), "User %s has no permission to transfer money", user.getUserName());
        Preconditions.checkArgument(kapper.hasRole("ROLE_KAPPER"), "The operation is forbidden. Money can be transfered only from user to kapper");
        Preconditions.checkArgument(user.getBalance().compareTo(amount) >= 0, "The user %s doesnt have enough money. On balance %s %s",
                user.getUserName(), user.getBalance(), user.getCurrency());
        try {
            user.setBalance(user.getBalance().subtract(amount));
            if (user.getCurrency().equals(kapper.getCurrency())) {
                kapper.setBalance(kapper.getBalance().add(amount));
                log.debug("Kapper {} got {} {}", kapper.getUserName(), amount, kapper.getCurrency());

            } else {
                BigDecimal resultAmount = exchange(user.getCurrency(), kapper.getCurrency(), amount);
                kapper.setBalance(kapper.getBalance().add(resultAmount));
                log.debug("Kapper {} got {} {}", kapper.getUserName(), resultAmount, kapper.getCurrency());
            }
            editUser(user);
            editUser(kapper);
            log.info("User {} transfered {} {} to kapper {}", user.getUserName(), amount, user.getCurrency(), kapper.getUserName());
        } catch (Exception e) {
            log.error("Couldn't transfer money from {} to {}", user.getUserName(), kapper.getUserName());
            throw new MoneyTransferException(e);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public BigDecimal exchange(String fromCurr, String toCurr, BigDecimal amount){
        log.debug("exchange(fromCurr: {}, toCurr: {}, amount: {})...", fromCurr, toCurr, amount);
        if (fromCurr.equals(toCurr)) {
            return amount;
        }
        Date date = Date.valueOf(LocalDate.now());
        CurrencyUtil util = new CurrencyUtil(currService);
        if (!currService.isExist(date, fromCurr) || !currService.isExist(date, toCurr)) {
            util.getCurrencyRatesForToday();
            date = util.getActualCurrRateDate(date, fromCurr, toCurr, false);

        }
        if (fromCurr.equals("RUB")) {
            CurrencyRate rate = currService.getCurrByDate(date, toCurr);
            return amount.divide(rate.getValue())
                    .multiply(BigDecimal.valueOf(rate.getNominal()));
        } else if (toCurr.equals("RUB")) {
            CurrencyRate rate = currService.getCurrByDate(date, fromCurr);
            return amount.multiply(rate.getValue())
                    .multiply(BigDecimal.valueOf(rate.getNominal()));
        }
        CurrencyRate from = currService.getCurrByDate(date, fromCurr);
        CurrencyRate to = currService.getCurrByDate(date, toCurr);
        BigDecimal amountInRub = amount.multiply(from.getValue())
                .multiply(BigDecimal.valueOf(from.getNominal()));
        return amountInRub.divide(to.getValue())
                .multiply(BigDecimal.valueOf(to.getNominal()));
    }
}
