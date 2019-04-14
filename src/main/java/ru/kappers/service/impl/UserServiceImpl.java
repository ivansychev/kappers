package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kappers.model.*;
import ru.kappers.repository.RolesRepository;
import ru.kappers.repository.UsersRepository;
import ru.kappers.service.CurrRateService;
import ru.kappers.service.RolesService;
import ru.kappers.service.UserService;
import ru.kappers.util.CurrencyUtil;
import ru.kappers.util.DateUtil;

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

    @Override
    @Transactional
    public synchronized void transfer(User user, User kapper, double amount) {
        if (!user.hasRole("ROLE_USER")) {
            throw new IllegalArgumentException("User " + user.getUserName() + " has no permission to transfer money");
        }
        if (!kapper.hasRole("ROLE_KAPPER")) {
            throw new IllegalArgumentException("The operation is forbidden. Money can be transfered only from user to kapper");
        }
        if (user.getBalance() < amount) {
            throw new IllegalArgumentException("The user " + user.getUserName() + " doesnt have enough money. On balance " + user.getBalance() + " " + user.getCurrency());
        }
        try {
            if (user.getCurrency().equals(kapper.getCurrency())) {
                user.setBalance(user.getBalance() - amount);
                kapper.setBalance(kapper.getBalance() + amount);
            } else {
                user.setBalance(user.getBalance() - amount);
                amount = exchange(user.getCurrency(), kapper.getCurrency(), amount);
                kapper.setBalance(kapper.getBalance() + amount);
                editUser(user);
                editUser(kapper);
            }
            log.info("User " + user.getUserName() + " transfered " + amount + " " + kapper.getCurrency() + " to kapper " + kapper.getUserName());
        } catch (Exception e) {
            log.error("Couldn't transfer money from " + user.getUserName() + " to " + kapper.getUserName());
        }
    }

    public double exchange(String fromCurr, String toCurr, double amount) {
        if (fromCurr.equals(toCurr)) {
            return amount;
        } else if (fromCurr.equals("RUB")) {
            CurrencyRate rate = currService.getCurrByDate(Date.valueOf(LocalDate.now()), toCurr);
            return amount / rate.getValue() * rate.getNominal();
        } else if (toCurr.equals("RUB")) {
            CurrencyRate rate = currService.getCurrByDate(Date.valueOf(LocalDate.now()), fromCurr);
            return amount * rate.getValue() * rate.getNominal();
        } else {
            CurrencyRate from = currService.getCurrByDate(Date.valueOf(LocalDate.now()), fromCurr);
            CurrencyRate to = currService.getCurrByDate(Date.valueOf(LocalDate.now()), toCurr);
            double amountInRub = amount * from.getValue() * from.getNominal();
            return amountInRub / to.getValue() * to.getNominal();
        }
    }
}
