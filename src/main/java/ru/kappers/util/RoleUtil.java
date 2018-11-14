package ru.kappers.util;

import lombok.extern.log4j.Log4j;
import ru.kappers.model.Role;

import java.sql.*;
import java.util.Objects;

@Log4j
@Deprecated
//todo думаю данный класс уже можно удалить
public class RoleUtil {
    private static Connection con;
    private static PreparedStatement stmt;
    private static ResultSet rs;


    public static Role getRoleNameById(Integer id) {
        String query = "select * from public.roles where id = ?";
        return performQuery(query, id);
    }

    public static Role getRoleIdByName(String roleName) {
        String query = "select * from public.roles where name = ?";
        return performQuery(query, roleName);
    }

//    private static Role performQuery(String s, Integer arg) {
//        return performQuery(s, String.valueOf(arg));
//    }

    private static Role performQuery(String s, Object arg) {
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

            return Role.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .build();
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
