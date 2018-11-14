package ru.kappers.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kappers.KappersApplication;
import ru.kappers.model.Role;
import ru.kappers.model.User;
import ru.kappers.repository.UsersRepository;
import ru.kappers.util.DateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@Log4j
@ActiveProfiles("test")
@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KappersApplication.class})
@TestExecutionListeners({DbUnitTestExecutionListener.class})
@DatabaseSetup("/data/UserServiceImplTest-users.xml")
public class UserServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UserService userService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RolesService rolesService;

    private User admin = User.builder()
            .userName("admin")
            .name("админ")
            .password("asasdgfas")
            .dateOfBirth(DateUtil.convertDate("19650806"))
            .currency("RUB")
            .lang("RUSSIAN")
            .build();
    private User user = User.builder()
            .userName("user")
            .name("юзер")
            .password("assaasas")
            .dateOfBirth(DateUtil.convertDate("19650806"))
            .currency("RUB")
            .lang("RUSSIAN")
            .build();
    private User kapper = User.builder()
            .userName("kapper")
            .name("каппер")
            .password("assaasas")
            .dateOfBirth(DateUtil.convertDate("19650806"))
            .currency("RUB")
            .lang("RUSSIAN")
            .build();

    private final Map<User, Integer> userRoleIdMap = new HashMap<>();
    {
        userRoleIdMap.put(admin, 1);
        userRoleIdMap.put(user, 2);
        userRoleIdMap.put(kapper, 3);
    }

    @Before
    public void setUp() {
        for (User u : userRoleIdMap.keySet()) {
            if (u.getRole() == null) {
                u.setRole(rolesService.getById(userRoleIdMap.get(u)));
            }
        }
    }


    @Test
    public void addUsers() {
        deleteFromTables("users");
        User userA = userService.addUser(admin);
        User userU = userService.addUser(user);
        User userK = userService.addUser(kapper);
        assertEquals(userA, admin);
        assertEquals(userU, user);
        assertEquals(userK, kapper);
    }

    @Test
    public void deleteUser() {
        final String userName = user.getUserName();
        userService.delete(usersRepository.getByUserName(userName));
        assertNull(usersRepository.getByUserName(userName));
    }

    @Test
    public void getByUserName() {
        User user1 = userService.getByUserName("kapper");
        log.info(user1.getDateOfRegistration());
        assertNotNull(user1);
        assertEquals(user1.getUserName(), kapper.getUserName());
        assertNotEquals(user1, user);
        assertNotEquals(user1, admin);
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
        final String adminUserName = "admin";
        User user1 = userService.getByUserName(adminUserName);
        String curr = user1.getCurrency();
        assertNotNull(user1);
        user1.setCurrency(curr.equals("USD") ? "RUB" : "USD");
        userService.editUser(user1);
        user1 = userService.getByUserName(adminUserName);
        assertNotEquals(curr, user1.getCurrency());
    }

    @Test
    public void getAll() {
        List<String> all = userService.getAll().stream()
                .map(User::getUserName)
                .collect(Collectors.toList());
        assertTrue(all.contains(admin.getUserName()));
        assertTrue(all.contains(user.getUserName()));
        assertTrue(all.contains(kapper.getUserName()));
    }

    @Test
    public void getAllByRole() {
        List<User> admins = userService.getAllByRole("ROLE_ADMIN");
        assertNotNull(admins);
        assertNotEquals(0, admins.size());
        assertTrue(admins.stream()
                .map(User::getUserName)
                .collect(Collectors.toList())
                .contains(admin.getUserName()));
    }

    @Test
    public void hasRole() {
        User userU = userService.getByUserName(user.getUserName());
        User userK = userService.getByUserName(kapper.getUserName());
        assertTrue(userService.hasRole(userK, "ROLE_KAPPER"));
        assertFalse(userService.hasRole(userU, "ROLE_KAPPER"));
        assertFalse(userService.hasRole(userK, "ROLE_ADMIN"));
    }

    @Test
    public void getRole() {
        User userK = userService.getByUserName(kapper.getUserName());
        assertNotNull(userK);
        Role role = userService.getRole(userK);
        assertNotNull(role);
        assertEquals(role.getName(), "ROLE_KAPPER");
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