package ru.kappers.service;

import lombok.extern.log4j.Log4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kappers.KappersApplication;
import ru.kappers.config.AdditionalBDConfig;
import ru.kappers.model.Roles;
import ru.kappers.model.User;
import ru.kappers.util.DateUtil;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { KappersApplication.class, AdditionalBDConfig.class })
public class UserServiceImplTest {

    @Autowired
    private UserService userService;
    private User admin;
    private User user;
    private User kapper;

    @Before
    public void addUsers() {
        admin = User.builder()
                .userName("admin")
                .name("админ")
                .password("asasdgfas")
                .dateOfBirth(DateUtil.convertDate("19650806"))
                .currency("RUB")
                .lang("RUSSIAN")
                .roleId(Roles.RoleType.ROLE_ADMIN.getId())
                .build();
        user = User.builder()
                .userName("user")
                .name("юзер")
                .password("assaasas")
                .dateOfBirth(DateUtil.convertDate("19650806"))
                .currency("RUB")
                .lang("RUSSIAN")
                .roleId(Roles.RoleType.ROLE_USER.getId())
                .build();
        kapper = User.builder()
                .userName("kapper")
                .name("каппер")
                .password("assaasas")
                .dateOfBirth(DateUtil.convertDate("19650806"))
                .currency("RUB")
                .lang("RUSSIAN")
                .roleId(Roles.RoleType.ROLE_KAPPER.getId())
                .build();
        userService.addUser(admin);
        log.debug("admin создан");
        userService.addUser(user);
        log.debug("user создан");
        userService.addUser(kapper);
        log.debug("kapper создан");

    }

    @After
    public void deleteUsers() {
        userService.delete(user);
        log.debug("user удален");

        userService.delete(admin);
        log.debug("admin удален");

        userService.delete(kapper);
        log.debug("kapper удален");

    }

    @Test
    public void delete() {
        User beforeDelete = user;
        userService.delete(user);
        User afterDelete = userService.getById(beforeDelete.getUserId());
        assertNull(afterDelete);
        userService.addUser(beforeDelete);
        afterDelete = userService.getByUserName(beforeDelete.getUserName());
        assertNotNull(afterDelete);
    }

    @Test
    public void getByUserName() {
        User user1 = userService.getByUserName("kapper");
        assertNotNull(user1);
        assertEquals(user1.getUserName(), kapper.getUserName());
        assertNotEquals(user1, user);
        assertNotEquals(user1, admin);
    }

    @Test
    public void getByName() {
        User user1 = userService.getByName("админ");
        assertNotNull(user1);
        assertNotEquals(user1, kapper);
        assertNotEquals(user1, user);
        assertEquals(user1.getUserName(), admin.getUserName());
    }

    @Test
    public void getById() {
//        User user1 = userService.getById(user.getUserId());
//        assertNotNull(user1);
//        assertNotEquals(user1, kapper);
//        assertEquals(user1, user);
//        assertNotEquals(user1, admin);
    }

    @Test
    public void editUser() {
        User user1 = userService.getByUserName("admin");
        String curr = user1.getCurrency();
        assertNotNull(user1);
        user1.setCurrency(curr.equals("USD")?"RUB":"USD");
        userService.editUser(user1);
        user1 = userService.getByUserName("admin");
        assertNotEquals(curr, user1.getCurrency());
    }

    @Test
    public void getAll() {
        List<String> all = userService.getAll().stream().map(s->s.getUserName()).collect(Collectors.toList());
        assertTrue(all.contains(user.getUserName()));
        assertTrue(all.contains(admin.getUserName()));
        assertTrue(all.contains(kapper.getUserName()));

    }

    @Test
    public void getAllByRole() {
//        List<User> admins = userService.getAllByRole("ROLE_ADMIN");
//        assertNotNull(admins);
//        assertNotEquals(0, admins.size());
//        assertTrue(admins.contains(admin));
    }

    @Test
    public void hasRole() {
//        assertTrue(userService.hasRole(kapper, "ROLE_KAPPER"));
//        assertFalse(userService.hasRole(user, "ROLE_KAPPER"));
//        assertFalse(userService.hasRole(kapper, "ROLE_ADMIN"));
        assertTrue(userService.hasRole(kapper, Roles.RoleType.ROLE_KAPPER.getId()));
    }

    @Test
    public void getRole() {
//        Roles role = userService.getRole(admin);
//        assertEquals(1, role.getRoleId());
    }

    @Test
    public void getHistory() {
        //TODO
    }

    @Test
    public void getStat() {
        //TODO
    }

    @Test
    public void getKapperInfo() {
        //TODO
    }

    @Test
    public void getInfo() {
        //TODO
    }
}