package com.lysenko.crudapp.view;

import com.lysenko.crudapp.controller.DeveloperController;
import com.lysenko.crudapp.controller.SkillsController;
import com.lysenko.crudapp.controller.SpecialtyController;

import java.util.Scanner;

public class MainView {
    private final Scanner scanner;
    private final DeveloperView developerView;
    private final SkillView skillView;
    private final SpecialtyView specialtyView;


    public MainView(DeveloperController developerController, SkillsController skillsController, SpecialtyController specialtyController, Scanner scanner) {
        this.scanner = scanner;
        this.specialtyView = new SpecialtyView(scanner, specialtyController);
        this.skillView = new SkillView(scanner, skillsController);
        this.developerView = new DeveloperView(scanner, developerController);
    }

    public void run() {
        printMainMenu();
        switch (scanner.next()) {
            case "1" :
                skillView.createSkill();
                run();
            case "2" :
                specialtyView.createSpecialty();
                run();
            case "3" :
                developerView.createNewDeveloper();
                run();
            case "4" :
                break;
            default:
                System.out.println("Only 1 to 3 available");
        } scanner.close();
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
