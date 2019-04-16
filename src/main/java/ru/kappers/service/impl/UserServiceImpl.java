package ru.kappers.service.impl;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kappers.model.*;
import ru.kappers.repository.UsersRepository;
import ru.kappers.service.CurrRateService;
import ru.kappers.service.RolesService;
import ru.kappers.service.UserService;
import ru.kappers.util.DateUtil;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository repository;
    private final RolesService rolesService;
    private final CurrRateService currService;

    @Autowired
    public UserServiceImpl(UsersRepository repository, RolesService rolesService, CurrRateService currService) {
        this.repository = repository;
        this.rolesService = rolesService;
        this.currService = currService;
    }

    @Override
    public User addUser(User user) {
        final String userName = user.getUserName();
        User byUserId = repository.getByUserName(userName);
        if (byUserId != null) {
            log.info("Пользователь {} уже существует", userName);
            return byUserId;
        }
        if (user.getDateOfRegistration() == null) {
            user.setDateOfRegistration(DateUtil.getCurrentTime());
        }
        if (user.getRole() == null) {
            user.setRole(rolesService.getByName("ROLE_USER"));
        }
        return repository.save(user);
    }

    @Override
    public void deleteByUserName(String userName) {
        repository.deleteByUserName(userName);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public User getByUserName(String name) {
        return repository.getByUserName(name);
    }

    @Override
    public User getByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public User getById(int id) {
        return repository.findById(id)
                .orElse(null);
    }

    @Override
    public User editUser(User user) {
        return repository.save(user);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public List<User> getAllByRole(String roleName) {
        Role role = rolesService.getByName(roleName);
        return repository.getAllByRoleId(role.getId());
    }

    @Override
    public boolean hasRole(User user, String roleName) {
        return user.hasRole(roleName);
    }

    @Override
    public boolean hasRole(User user, int roleId) {
        return user.hasRole(roleId);
    }

    @Override
    public boolean hasRole(User user, Role role) {
        return user.hasRole(role.getId());
    }

    @Override
    public Role getRole(User user) {
        return user.getRole();
    }

    @Override
    public History getHistory(User user) {
        //TODO
        return null;
    }

    @Override
    public Stat getStat(User user) {
        //TODO
        return null;
    }

    @Override
    public KapperInfo getKapperInfo(User user) {
        //TODO
        return null;
    }

    @Override
    public PersonalInfo getInfo(User user) {
        //TODO
        return null;
    }

    //TODO написать unit-тесты для transfer и exchange
    @Override
    @Transactional
    public synchronized void transfer(User user, User kapper, BigDecimal amount) {
        Preconditions.checkArgument(user.hasRole("ROLE_USER"), "User %s has no permission to transfer money", user.getUserName());
        Preconditions.checkArgument(kapper.hasRole("ROLE_KAPPER"), "The operation is forbidden. Money can be transfered only from user to kapper");
        Preconditions.checkArgument(user.getBalance().compareTo(amount) >= 0, "The user %s doesnt have enough money. On balance %s %s",
                user.getUserName(), user.getBalance(), user.getCurrency());
        try {
            user.setBalance(user.getBalance().subtract(amount));
            if (user.getCurrency().equals(kapper.getCurrency())) {
                kapper.setBalance(kapper.getBalance().add(amount));
            } else {
                kapper.setBalance(kapper.getBalance().add(
                        exchange(user.getCurrency(), kapper.getCurrency(), amount)));
            }
            editUser(user);
            editUser(kapper);
            log.info("User {} transfered {} {} to kapper {}", user.getUserName(), amount, user.getCurrency(), kapper.getUserName());
        } catch (Exception e) {
            log.error("Couldn't transfer money from {} to {}", user.getUserName(), kapper.getUserName());
        }
    }

    public BigDecimal exchange(String fromCurr, String toCurr, BigDecimal amount) {
        if (fromCurr.equals(toCurr)) {
            return amount;
        }
        final Date date = Date.valueOf(LocalDate.now());
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
