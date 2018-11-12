package ru.kappers.service;

import lombok.extern.log4j.Log4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kappers.KappersApplication;
import ru.kappers.model.Roles;
import ru.kappers.model.User;
import ru.kappers.util.DateUtil;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@Log4j
@ActiveProfiles("test")
@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KappersApplication.class})
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    private User admin = User.builder()
            .userName("admin")
            .name("админ")
            .password("asasdgfas")
            .dateOfBirth(DateUtil.convertDate("19650806"))
            .currency("RUB")
            .lang("RUSSIAN")
            .roleId(1)
            .build();
    private User user = User.builder()
            .userName("user")
            .name("юзер")
            .password("assaasas")
            .dateOfBirth(DateUtil.convertDate("19650806"))
            .currency("RUB")
            .lang("RUSSIAN")
            .roleId(2)
            .build();
    private User kapper = User.builder()
            .userName("kapper")
            .name("каппер")
            .password("assaasas")
            .dateOfBirth(DateUtil.convertDate("19650806"))
            .currency("RUB")
            .lang("RUSSIAN")
            .roleId(3)
            .build();

    @Test
    public void addUsers() {
        User userA = userService.addUser(admin);
        User userU = userService.addUser(user);
        User userK = userService.addUser(kapper);
        assertEquals(userA, admin);
        assertEquals(userU, user);
        assertEquals(userK, kapper);
        userService.delete(userA);
        userService.delete(userU);
        userService.delete(userK);
    }

    //TODO переписать этот тест так, чтоб создавались сущности один раз, а потом с ними можно было бы поработать и в конце чтоб они удалялись. Контекст межу тестами и основной программой должен быть разный
    @Test
    public void delete() {
        User beforeDelete = user;
        userService.addUser(beforeDelete);
        userService.delete(user);
        User afterDelete = userService.getByUserName(beforeDelete.getUserName());
        assertNull(afterDelete);
    }

    @Test
    public void getByUserName() {
        userService.addUser(kapper);
        User user1 = userService.getByUserName("kapper");
        System.out.println(user1.getDateOfRegistration());
        assertNotNull(user1);
        assertEquals(user1.getUserName(), kapper.getUserName());
        assertNotEquals(user1, user);
        assertNotEquals(user1, admin);
        userService.delete(kapper);
    }

    @Test
    public void getByName() {
        userService.addUser(admin);
        User user1 = userService.getByName("админ");
        assertNotNull(user1);
        assertNotEquals(user1, kapper);
        assertNotEquals(user1, user);
        assertEquals(user1.getUserName(), admin.getUserName());
        userService.delete(admin);
    }

    @Test
    public void editUser() {
        userService.addUser(admin);
        User user1 = userService.getByUserName("admin");
        String curr = user1.getCurrency();
        assertNotNull(user1);
        user1.setCurrency(curr.equals("USD") ? "RUB" : "USD");
        userService.editUser(user1);
        user1 = userService.getByUserName("admin");
        assertNotEquals(curr, user1.getCurrency());
        userService.delete(admin);
    }

    @Test
    public void getAll() {
        User userA = userService.addUser(admin);
        User userU = userService.addUser(user);
        User userK = userService.addUser(kapper);
        List<String> all = userService.getAll().stream().map(User::getUserName).collect(Collectors.toList());
        assertTrue(all.contains(userA.getUserName()));
        assertTrue(all.contains(userU.getUserName()));
        assertTrue(all.contains(userK.getUserName()));
        userService.delete(userA);
        userService.delete(userU);
        userService.delete(userK);
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
        User userA = userService.addUser(admin);
        User userU = userService.addUser(user);
        User userK = userService.addUser(kapper);
        assertTrue(userService.hasRole(kapper, "ROLE_KAPPER"));
        assertFalse(userService.hasRole(user, "ROLE_KAPPER"));
        assertFalse(userService.hasRole(kapper, "ROLE_ADMIN"));
        userService.delete(userA);
        userService.delete(userU);
        userService.delete(userK);
    }

    @Test
    public void getRole() {
     //   userService.addUser(kapper);
//        User user1 = userService.getByUserName("kapper");
//        assertNotNull(user1);
//        Roles role = userService.getRole(user1); //TODO не работает!!!
//        assertNotNull(role);
     //   assertTrue(user1.hasRole("ROLE_KAPPER"));
//        assertEquals(userService.getRole(user1).getRoleName(), "ROLE_KAPPER");

//todo срочно
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