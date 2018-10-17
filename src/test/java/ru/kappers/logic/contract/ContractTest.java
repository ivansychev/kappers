package ru.kappers.logic.contract;

import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.kappers.model.KapperInfo;
import ru.kappers.model.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.Assert.*;

public class ContractTest {

    private static User entity1;
    private static User entity2;
    private static User entity3;
    private static User entity4;
    private static KapperInfo kapper1;
    private static KapperInfo kapper2;
    private static KapperInfo kapper3;
    private static KapperInfo kapper4;
    private static Session session;
    private static Contract contract;

    @BeforeClass
    public static void initData() {
//        session = HibernateSessionFactory.getSession();
//        contract = new Contract(session);
//        entity1 = new Users();
//        entity2 = new Users();
//        entity3 = new Users();
//        entity4 = new Users();
//
//        entity1.setUserName("user1");
//        entity1.setName("user1");
//        entity1.setRoleId(3);
//        entity1.setPassword("user1");
//        entity1.setEmail("user1@mail.ru");
//        entity1.setDateOfRegistration(new Timestamp(System.currentTimeMillis()));
//        entity1.setDateOfBirth(Timestamp.valueOf(LocalDateTime.of(1985, 4, 29, 1, 45)));
//
//        entity2.setUserName("user2");
//        entity2.setName("user2");
//        entity2.setRoleId(2);
//        entity2.setPassword("user2");
//        entity2.setEmail("user2@mail.ru");
//        entity2.setDateOfRegistration(new Timestamp(System.currentTimeMillis()));
//        entity2.setDateOfBirth(Timestamp.valueOf(LocalDateTime.of(1988, 8, 15, 18, 45)));
//
//        entity3.setUserName("user3");
//        entity3.setName("user3");
//        entity3.setRoleId(1);
//        entity3.setPassword("user3");
//        entity3.setEmail("user3@mail.ru");
//        entity3.setDateOfRegistration(new Timestamp(System.currentTimeMillis()));
//        entity3.setDateOfBirth(Timestamp.valueOf(LocalDateTime.of(1988, 8, 15, 18, 45)));
//
//        entity4.setUserName("user4");
//        entity4.setName("user4");
//        entity4.setRoleId(3);
//        entity4.setPassword("user4");
//        entity4.setEmail("user4@mail.ru");
//        entity4.setDateOfRegistration(new Timestamp(System.currentTimeMillis()));
//        entity4.setDateOfBirth(Timestamp.valueOf(LocalDateTime.of(1985, 4, 29, 1, 45)));
//        session.beginTransaction();
//        session.saveOrUpdate(entity1);
//        session.saveOrUpdate(entity2);
//        session.saveOrUpdate(entity3);
//        session.saveOrUpdate(entity4);
//        session.getTransaction().commit();
    }

    @Test
    public void a_initCapper() throws Exception {
//        kapper1 = contract.initCapper(entity1);
//        kapper4 = contract.initCapper(entity4);
//        kapper2 = contract.initCapper(entity2);
//        kapper3 = contract.initCapper(entity3);
//        if (kapper1 != null) {
//            session.saveOrUpdate(kapper1);
//        }
//        if (kapper2 != null) {
//            session.saveOrUpdate(kapper2);
//        }
//        if (kapper3 != null) {
//            session.saveOrUpdate(kapper3);
//        }
//        if (kapper4 != null) {
//            session.saveOrUpdate(kapper4);
//        }
//
//        Assert.assertNotNull(kapper1);
//        Assert.assertNotNull(kapper4);
//        Assert.assertEquals(kapper1.getTokens(), 500d, 0.001);
//        Assert.assertEquals(kapper4.getTokens(), 500d, 0.001);
//        Assert.assertNull(kapper2);
//        Assert.assertNull(kapper3);
    }

    @Test
    public void b_getKapperInfo() throws Exception {
//        KapperInfo k = contract.getKapperInfo(kapper1.getUser());
//        Assert.assertEquals(k.getTokens(), 500d, 0.001);
//        Assert.assertEquals(k.getBlockedTokens(), 0d, 0.001);
//        Assert.assertEquals((int) k.getBets(), 0);
//        Assert.assertEquals((int) k.getFailBets(), 0);
//        Assert.assertEquals((int) k.getSuccessBets(), 0);
    }

    @Test
    public void d_blockTokens() throws Exception {
//        contract.blockTokens(kapper1.getUser(), 50);
//        Assert.assertEquals(kapper1.getBlockedTokens(), 50, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void d_blockTokensUpperSum() throws Exception {
   //     contract.blockTokens(kapper1.getUser(), 600);
    }

    @Test
    public void e_unblockAmount() throws Exception {
//        contract.unblockAmount(kapper1.getUser(), 5);
//        Assert.assertEquals(kapper1.getBlockedTokens(), 45, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_unblockAmountUpperSum() throws Exception {
       // contract.unblockAmount(kapper1.getUser(), 1000);
    }

    @Test
    public void f_getBalance() throws Exception {
//        Map<User, Double> map = contract.getBalance();
//        Assert.assertTrue(map.containsKey(entity1));
//        Assert.assertTrue(map.containsKey(entity4));
//        Assert.assertEquals(map.get(entity1), 500d, 0.001);
//        Assert.assertEquals(map.get(entity4), 500d, 0.001);
    }

    @Test
    public void g_getAllInfo() throws Exception {
//        Map<User, KapperInfo> map = contract.getAllInfo();
//        Assert.assertTrue(map.containsKey(entity1));
//        Assert.assertTrue(map.containsKey(entity4));
//        Assert.assertEquals(kapper1, map.get(entity1));
//        Assert.assertEquals(kapper4, map.get(entity4));
    }

    @Test
    public void h_deleteKapper() throws Exception {
//        session.beginTransaction();
//        session.delete(entity2);
//        session.getTransaction().commit();
//        Users en = getUserEntity(entity2.getUserId());
//        Assert.assertNull(en);
    }

    private User getUserEntity(int id) {
//        try {
//            return session.get(Users.class, id);
//        } catch (Exception e) {
//            return null;
//        }
        return null;
    }

    @AfterClass
    public static void i_closeSession() {
//        contract.deleteKapper(entity1);
//        contract.deleteKapper(entity4);
//        session.beginTransaction();
//        session.delete(entity3);
//        session.flush();
//        session.getTransaction().commit();
//        session.close();
    }
}