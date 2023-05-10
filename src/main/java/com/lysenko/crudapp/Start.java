package com.lysenko.crudapp;

import com.lysenko.crudapp.configuration.JDBCConnection;
import com.lysenko.crudapp.configuration.LiquibaseProp;
import com.lysenko.crudapp.controller.DeveloperController;
import com.lysenko.crudapp.controller.SkillsController;
import com.lysenko.crudapp.controller.SpecialtyController;
import com.lysenko.crudapp.jdbcRepository.impl.DeveloperRepositoryImpl;
import com.lysenko.crudapp.jdbcRepository.impl.SkillRepositoryImpl;
import com.lysenko.crudapp.jdbcRepository.impl.SpecialtyRepositoryImpl;
import com.lysenko.crudapp.view.MainView;

import java.sql.Connection;
import java.util.Scanner;

public class Start {
    public static void main(String[] args) {
        LiquibaseProp liquibaseProp = new LiquibaseProp();

        try {
            JDBCConnection jdbcConnection = new JDBCConnection();
            Connection connection = jdbcConnection.get();
            connection.setAutoCommit(false);

            liquibaseProp.runMigrations(connection);

            DeveloperRepositoryImpl developerRepository = new DeveloperRepositoryImpl(connection);
            SkillRepositoryImpl skillRepository = new SkillRepositoryImpl(connection);
            SpecialtyRepositoryImpl specialtyRepository = new SpecialtyRepositoryImpl(connection);

            DeveloperController developerController = new DeveloperController(developerRepository);
            SkillsController skillsController = new SkillsController(skillRepository);
            SpecialtyController specialtyController = new SpecialtyController(specialtyRepository);
            Scanner scanner = new Scanner(System.in);

            MainView view = new MainView(developerController, skillsController, specialtyController, scanner);
            view.run();

            scanner.close();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
