package com.lysenko.crudapp;

import com.lysenko.crudapp.configuration.JDBCConnection;
import com.lysenko.crudapp.configuration.LiquibaseProp;
import com.lysenko.crudapp.controller.DeveloperController;
import com.lysenko.crudapp.controller.SkillsController;
import com.lysenko.crudapp.controller.SpecialtyController;
import com.lysenko.crudapp.view.MainView;

import java.sql.Connection;
import java.sql.SQLException;

public class Start {
    public static void main(String[] args) {
        LiquibaseProp liquibaseProp = new LiquibaseProp();
        try {
            liquibaseProp.runMigrations();
        } catch (Exception e) {
            System.out.println("Can't run migrations " + e);
        }

        try {
            JDBCConnection jdbcConnection = new JDBCConnection();
            Connection connection = jdbcConnection.get();
            connection.setAutoCommit(false);
            DeveloperController developerController = new DeveloperController(jdbcConnection.get());
            SkillsController skillsController = new SkillsController(jdbcConnection.get());
            SpecialtyController specialtyController = new SpecialtyController(jdbcConnection.get());
            MainView view = new MainView(developerController, skillsController, specialtyController);
            view.run();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
