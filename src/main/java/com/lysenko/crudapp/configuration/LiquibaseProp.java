package com.lysenko.crudapp.configuration;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;


public class LiquibaseProp {
    private static final String CHANGELOG_FILE = "db/changelog.xml";

    public void runMigrations(Connection connection) throws LiquibaseException {

        // Создаем объект Liquibase
        Liquibase liquibase = new Liquibase(CHANGELOG_FILE, new ClassLoaderResourceAccessor(),
            DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection)));

        // Запускаем миграции
        liquibase.update("");
    }
}
