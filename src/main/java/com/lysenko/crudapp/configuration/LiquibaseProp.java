package com.lysenko.crudapp.configuration;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class LiquibaseProp {
    private static final String CHANGELOG_FILE = "db/changelog.xml";
    private static final String DATABASE_URL = "jdbc:mysql://127.0.01:3306/crud";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "secret";

    public void runMigrations() throws SQLException, LiquibaseException {
        // Создаем подключение к базе данных
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

        // Создаем объект Liquibase
        Liquibase liquibase = new Liquibase(CHANGELOG_FILE, new ClassLoaderResourceAccessor(),
            DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection)));

        // Запускаем миграции
        liquibase.update("");

        // Закрываем подключение к базе данных
        connection.close();
    }
}
