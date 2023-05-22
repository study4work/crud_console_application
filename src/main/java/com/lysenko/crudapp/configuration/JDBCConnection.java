package com.lysenko.crudapp.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnection {
    private static final String PASSWORD = "secret";
    private static final String USERNAME = "root";
    private static final String URL = "jdbc:mysql://127.0.01:3306/crud";

    private static Connection connection;

    public static Connection getConnection() {
        if(connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException("Can,t create connection " +  e);
            }
        }
        return connection;
    }

    public static PreparedStatement preparedStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }

    public static PreparedStatement preparedStatementWithKeys(String sql) throws SQLException {
        return getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }
}
