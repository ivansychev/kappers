package ru.kappers.util;

import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.Roles;
import ru.kappers.service.RolesService;

import java.sql.*;
import java.util.List;
import java.util.Objects;

@Log4j
public class RoleUtil {
    private static Connection con;
    private static PreparedStatement stmt;
    private static ResultSet rs;


    public static Roles getRoleNameById(Integer id) {
        String query = "select * from public.roles where role_id = ?";
        return performQuery(query, id);
    }

    public static Roles getRoleIdByName(String roleName) {
        String query = "select * from public.roles where role_name = ?";
        return performQuery(query, roleName);
    }

//    private static Roles performQuery(String s, Integer arg) {
//        return performQuery(s, String.valueOf(arg));
//    }

    private static Roles performQuery(String s, Object arg) {
        try {
            con = UtilDBConfig.getConnection();
            stmt = Objects.requireNonNull(con).prepareStatement(s);
            if (arg instanceof Integer) {
                stmt.setInt(1, (int) arg);
            } else {
                stmt.setString(1, (String) arg);
            }
            rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new RuntimeException("Запрос не вернул результатов");
            }

            return Roles.builder().roleId(rs.getInt("role_id")).roleName(rs.getString("role_name")).build();
        } catch (SQLException e) {
            log.error("Не удалось получить роль." + e.getLocalizedMessage());
            return null;
        } finally {
            try {
                if (con != null)
                    con.close();
                stmt.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
