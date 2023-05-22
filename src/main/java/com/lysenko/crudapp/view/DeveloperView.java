package com.lysenko.crudapp.view;

import com.lysenko.crudapp.controller.DeveloperController;
import com.lysenko.crudapp.model.Developer;
import com.lysenko.crudapp.model.Skill;
import com.lysenko.crudapp.model.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeveloperView {

    private final Scanner scanner;
    private final DeveloperController developerController;

    public DeveloperView(Scanner scanner, DeveloperController developerController) {
        this.developerController = developerController;
        this.scanner = scanner;
    }

    public void createNewDeveloper() {
        printDeveloperMenu();
        switch (scanner.next()) {
            case "1":
                System.out.println("Enter first name:");
                String firstName = scanner.next();
                System.out.println("Enter last name:");
                String lastName = scanner.next();

                System.out.println("Enter specialty description");
                String specialtyDescription = scanner.next();

                System.out.println("Do you want to add a new Skill?");
                List<Skill> skillList = new ArrayList<>();
                while (scanner.next().equals("y")) {
                    Skill skill = new Skill();
                    System.out.println("Enter skill description");
                    skill.setSkillDescription(scanner.next());
                    skill.setStatus(Status.ACTIVE);
                    skillList.add(skill);
                    System.out.println("Do you want to add a new Skill?");
                }
                developerController.createDeveloper(firstName, lastName, specialtyDescription, skillList);
                System.out.println("Developer created");
            break;
            case "2":
                System.out.println("Enter id to find developer: ");
                long idToFind = scanner.nextLong();
                System.out.println("Your developer: " + developerController.findById(idToFind));
                break;
            case "3":
                System.out.print(developerController.findAll());
                break;
            case "4":
                System.out.println("Enter old id of developer to update: ");
                long devId = scanner.nextLong();
                System.out.println("Enter first name:");
                String newFirstName = scanner.next();
                System.out.println("Enter last name:");
                String newLastName = scanner.next();
                Developer updateDeveloper = new Developer(devId, newFirstName, newLastName);
                System.out.println("developer have been updated" + developerController.update(updateDeveloper));
                break;
            case "5":
                System.out.println("Enter id to find developer: ");
                long idToDelete = scanner.nextLong();
                developerController.delete(idToDelete);
                System.out.println("Developer have been deleted");
                break;
            case "6": break;
        }
    }

    private void printDeveloperMenu() {
        System.out.println("----------");
        System.out.println("We need to make developer");
        System.out.println("Please enter the form below");
        System.out.println("----------");
        System.out.println("Select 1 to create developer");
        System.out.println("Select 2 to find developer by id : insert id :");
        System.out.println("Select 3 to show all developer");
        System.out.println("Select 4 to update developer:");
        System.out.println("Select 5 to delete developer by id: insert id");
        System.out.println("Select 6 to return");
        System.out.println("----------");
    }
}
