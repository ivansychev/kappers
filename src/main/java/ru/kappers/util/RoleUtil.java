package ru.kappers.util;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.Roles;
import ru.kappers.service.RolesService;

import java.util.List;

public class RoleUtil {
    private static Session session = HibernateSessionFactory.getSession();

    public static Roles getRoleNameById(int id) {
        return session.get(Roles.class, id);

    }

    public static Roles getRoleIdByName(String roleName) {
        List list = session.createQuery("from Roles where role_name = " + roleName).list();
        if (list.size() > 0) return (Roles) list.get(0);
        return null;
    }
}
