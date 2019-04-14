package ru.kappers.service;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.DCoreSdk;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.*;
import ch.decent.sdk.net.TrustAllCerts;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import kotlin.Pair;
import lombok.extern.log4j.Log4j;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.threeten.bp.LocalDateTime;
import ru.kappers.KappersApplication;
import ru.kappers.model.Role;
import ru.kappers.model.User;
import ru.kappers.repository.UsersRepository;
import ru.kappers.util.DateUtil;

import java.util.*;
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
            .lang("RUSSIAN")
            .build();
    private User user = User.builder()
            .userName("user")
            .name("юзер")
            .password("assaasas")
            .dateOfBirth(DateUtil.convertDate("19650806"))
            .lang("RUSSIAN")
            .build();
    private User kapper = User.builder()
            .userName("kapper")
            .name("каппер")
            .password("assaasas")
            .dateOfBirth(DateUtil.convertDate("19650806"))
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
        String curr = user1.getEmail();
        assertNotNull(user1);
        user1.setEmail("qwe1as@asas.ru");
        userService.editUser(user1);
        user1 = userService.getByUserName(adminUserName);
        assertNotEquals(curr, user1.getEmail());
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
        assertTrue(userService.hasRole(userU, rolesService.getRoleIdByName("ROLE_USER")));
        assertTrue(userService.hasRole(userU, rolesService.getByName("ROLE_USER")));
        assertTrue(userService.hasRole(userU, rolesService.getById(2)));
        assertEquals(userK.getRole(), rolesService.getByName("ROLE_KAPPER"));
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

//    @Test
//    public void DCoreExample() {
//        final Logger slf4jLog = LoggerFactory.getLogger("SDK_SAMPLE");
///*      from DCore Kotlin SDK
//        final String accountName = "u961279ec8b7ae7bd62f304f7c1c3d345";
//        final String accountPrivateKey = "5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn";
//        final String webSocketUrl = "wss://stagesocket.decentgo.com:8090";
//        final String httpUrl = "https://stagesocket.decentgo.com:8090";
//*/
//        final String accountID = "1.2.371";
//        final String accountName = "dw-kapper";
//        final String accountPrivateKey = "5Kim38ED5obSB9urFWgR4HXbQCbcPwcLUGG1xxaxPgYDBB9N7zX";
//        final String webSocketUrl = "wss://hackathon2.decent.ch:8090";
//        final String httpUrl = "https://hackathon2.decent.ch:8090";
//
////        setup dcore api
//        OkHttpClient client = TrustAllCerts.wrap(new OkHttpClient().newBuilder()).build();
//        DCoreApi api = DCoreSdk.create(client, webSocketUrl, httpUrl, slf4jLog);
//
////        create user credentials
//        Credentials credentials = api.getAccountApi()
//                .createCredentials(accountName, accountPrivateKey)
//                .blockingGet();
//
////        balance
//        Pair<Asset, AssetAmount> balance = api.getBalanceApi()
//                .getBalanceWithAsset(credentials.getAccount(), "DCT")
//                .blockingGet();
//        log.info("BALANCE: {}" + balance.getFirst().format(balance.getSecond().getAmount(), 2));
//
//        String uri = "https://8sfaasfghfghfhertsdfsdffsha34uuuuuu.com";
//        String synopsis = "{\"content_type_id\": \"0\", \"title\": \"Hello world\", \"description\": \"\"}";
//        String hashCode = "5432106789543210678954321067890123456781"; // must 40 chars
//        LocalDateTime experation = LocalDateTime.parse("2018-12-08T13:31:15");
//        ContentSubmitOperation contentSubmitOperation = new ContentSubmitOperation(1, ChainObject.parse(accountID),
//                new ArrayList<>(), uri, 0, Arrays.asList(new RegionalPrice(new AssetAmount(0), Regions.ALL.getId())),
//                hashCode, new ArrayList<>(), new ArrayList<>(),
//                experation, new AssetAmount(0), synopsis, null, BaseOperation.FEE_UNSET
//                );
//        log.info("submit operation :" + contentSubmitOperation);
//        TransactionConfirmation confirmation = api.getBroadcastApi().broadcastWithCallback(accountPrivateKey, contentSubmitOperation)
//                .blockingGet();
//        log.info("TRANSACTION: " + confirmation);
//
//
////        transfer
///*
//        AssetAmount amount = balance.getFirst().amount(0.12345);
//        confirmation = api.getOperationsHelper()
//                .transfer(credentials, "u3a7b78084e7d3956442d5a4d439dad51", amount, "hello memo")
//                .blockingGet();
//        log.info("TRANSACTION: " + confirmation);
//*/
//
////        verify
////        ProcessedTransaction trx = api.getTransactionApi().getTransaction(confirmation)
////                .blockingGet();
////        log.info("TRANSACTION EXIST: " + trx);
//
////        history
//        List<OperationHistory> history = api.getHistoryApi().getAccountHistory(credentials.getAccount())
//                .blockingGet();
//        log.info("HISTORY: {}" + history);
//    }
}