package ru.kappers.logic.contract;

import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import ru.kappers.model.KapperInfo;
import ru.kappers.model.User;

import static org.junit.Assert.*;

@Ignore("Not implemented yet")
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
          }

    @Test
    public void a_initCapper() throws Exception {

    }

    @Test
    public void b_getKapperInfo() throws Exception {

    }

    @Test
    public void d_blockTokens() throws Exception {

    }

    @Test(expected = IllegalArgumentException.class)
    public void d_blockTokensUpperSum() throws Exception {
        throw new IllegalArgumentException();
      }

    @Test
    public void e_unblockAmount() throws Exception {

    }

    @Test(expected = IllegalArgumentException.class)
    public void e_unblockAmountUpperSum() throws Exception {
        throw new IllegalArgumentException();

    }

    @Test
    public void f_getBalance() throws Exception {

    }

    @Test
    public void g_getAllInfo() throws Exception {

    }

    @Test
    public void h_deleteKapper() throws Exception {

    }

    @AfterClass
    public static void i_closeSession() {

    }
}