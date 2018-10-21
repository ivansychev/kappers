package ru.kappers.util;

import org.junit.Assert;
import org.junit.Test;
import ru.kappers.model.Roles;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class RoleUtilTest {

    @Test
    public void getRoleNameById() {
        Roles roleNameById = RoleUtil.getRoleNameById((Integer)1);
        Assert.assertNotNull(roleNameById);
        Assert.assertEquals(roleNameById.getRoleName(), "ROLE_ADMIN");
    }

    @Test
    public void getRoleIdByName(){
        Roles role = RoleUtil.getRoleIdByName("ROLE_USER");
        Assert.assertNotNull(role);
        Assert.assertEquals(role.getRoleId(), 2);
    }
}