package com.lysenko.crudapp.view;

import com.lysenko.crudapp.configuration.JDBCConnection;
import com.lysenko.crudapp.configuration.LiquibaseProp;
import com.lysenko.crudapp.controller.DeveloperController;
import com.lysenko.crudapp.controller.SkillsController;
import com.lysenko.crudapp.controller.SpecialtyController;
import com.lysenko.crudapp.repository.jdbc.DeveloperRepositoryImpl;
import com.lysenko.crudapp.repository.jdbc.SkillRepositoryImpl;
import com.lysenko.crudapp.repository.jdbc.SpecialtyRepositoryImpl;

import java.sql.Connection;
import java.util.Scanner;

public class MainView {

    public void run() {
        LiquibaseProp liquibaseProp = new LiquibaseProp();
        try {
            Connection connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);

            liquibaseProp.runMigrations(connection);

            Scanner scanner = new Scanner(System.in);

            SkillRepositoryImpl skillRepository = new SkillRepositoryImpl();
            SpecialtyRepositoryImpl specialtyRepository = new SpecialtyRepositoryImpl();
            DeveloperRepositoryImpl developerRepository = new DeveloperRepositoryImpl(specialtyRepository, skillRepository);

            DeveloperController developerController = new DeveloperController(developerRepository);
            SkillsController skillsController = new SkillsController(skillRepository);
            SpecialtyController specialtyController = new SpecialtyController(specialtyRepository);

            DeveloperView developerView = new DeveloperView(scanner, developerController);
            SkillView skillView = new SkillView(scanner, skillsController);
            SpecialtyView specialtyView = new SpecialtyView(scanner, specialtyController);

            mainRun(scanner, developerView, skillView, specialtyView);
            connection.commit();

            scanner.close();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void mainRun(Scanner scanner, DeveloperView developerView, SkillView skillView, SpecialtyView specialtyView) {
        printMainMenu();
        switch (scanner.next()) {
            case "1" :
                skillView.createSkill();
                mainRun(scanner, developerView, skillView, specialtyView);
            case "2" :
                specialtyView.createSpecialty();
                mainRun(scanner, developerView, skillView, specialtyView);
            case "3" :
                developerView.createNewDeveloper();
                mainRun(scanner, developerView, skillView, specialtyView);
            case "4" :
                return;
            default:
                System.out.println("Only 1 to 3 available");
        }
    }

    private void printMainMenu() {
        System.out.println("----------");
        System.out.println("1. Skills menu");
        System.out.println("2. Specialty menu");
        System.out.println("3. Developer menu");
        System.out.println("4. Exit");
        System.out.println("----------");
    }
}
