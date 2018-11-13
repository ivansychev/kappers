package ru.kappers.util;

import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.kappers.model.Roles;

import java.sql.SQLException;

import static org.junit.Assert.*;
@Log4j
@ActiveProfiles("test")
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