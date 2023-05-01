package com.lysenko.crudapp.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
    private static final String PASSWORD = "secret";
    private static final String USERNAME = "root";
    private static final String URL = "jdbc:mysql://127.0.01:3306/crud";

    public Connection get() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
