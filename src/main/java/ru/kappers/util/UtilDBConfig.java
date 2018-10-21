package ru.kappers.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class UtilDBConfig {

    private static final  String  host     = "jdbc:postgresql://localhost:5432/testdb"  ; // сервер базы данных
    private static final  String  username = "postgres"; // учетная запись пользователя
    private static final  String  password = "postgres"; // пароль пользователя

    private static final  String  driverName = "org.postgresql.Driver";

    public static Connection getConnection() throws SQLException
    {
        try {
            Class.forName(driverName);
            Connection connection = DriverManager.getConnection(host, username, password);

            if (connection == null)
                System.err.println("Нет соединения с БД!");
            else
                return connection;
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException : " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQLException : " + e.getMessage());
        }
        return null;
    }
}
