package com.lysenko.crudapp.view;

import java.util.Scanner;

public class MainView {
    private final Scanner scanner;
    private final DeveloperView developerView;
    private final SkillView skillView;
    private final SpecialtyView specialtyView;


    public MainView() {
        this.scanner = new Scanner(System.in);
        this.developerView = new DeveloperView();
        this.specialtyView = new SpecialtyView();
        this.skillView = new SkillView();
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
                System.out.println("Only 1 to 3 availible");
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
