package ru.kappers.util;

import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.kappers.model.Role;

@Log4j
@ActiveProfiles("test")
@Ignore
public class RoleUtilTest {

    @Test
    public void getRoleNameById() {
        Role roleNameById = RoleUtil.getRoleNameById(1);
        Assert.assertNotNull(roleNameById);
        Assert.assertEquals(roleNameById.getName(), "ROLE_ADMIN");
    }

    @Test
    public void getRoleIdByName(){
        Role role = RoleUtil.getRoleIdByName("ROLE_USER");
        Assert.assertNotNull(role);
        Assert.assertEquals(role.getId(), 2);
    }
}