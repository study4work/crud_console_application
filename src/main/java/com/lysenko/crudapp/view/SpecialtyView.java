package com.lysenko.crudapp.view;

import com.lysenko.crudapp.controller.SpecialtyController;
import com.lysenko.crudapp.model.Specialty;
import com.lysenko.crudapp.model.Status;

import java.util.Scanner;

public class SpecialtyView {

    private final Scanner scanner;
    private final SpecialtyController specialtyController;

    public SpecialtyView(Scanner scanner, SpecialtyController specialtyController) {
        this.specialtyController = specialtyController;
        this.scanner = scanner;
    }

    public void createSpecialty() {
        printSpecialityMenu();
        switch (scanner.next()) {
            case "1" :
                System.out.println("1. Enter specialty description");
                String description = scanner.next();
                specialtyController.createSpecialty(description);
                System.out.println("Specialty created");
                break;
            case "2":
                System.out.println(specialtyController.findAll());
                break;
            case "3":
                System.out.println("Enter new description");
                String updateDescription = scanner.next();
                Specialty updateSpecialty = new Specialty(updateDescription, Status.ACTIVE);
                specialtyController.update(updateSpecialty);
                System.out.println("specialty have been updated");
                break;
            case "4":
                System.out.println("Enter specialty id to delete");
                long specId = scanner.nextLong();
                specialtyController.delete(specId);
                System.out.println("specialty have been deleted");
                break;
            case "5":
                System.out.println("Assigned specialty to developer: ");
                System.out.println("Enter specialty_id: ");
                long specialtyId = scanner.nextLong();
                System.out.println("Enter developer_id: ");
                long developerId = scanner.nextLong();
                specialtyController.setSpecialtyToDeveloper(specialtyId, developerId);
                break;
            case "6":
                break;
        }
    }

    private void printSpecialityMenu() {
        System.out.println("----------");
        System.out.println("We need to make specialty for our developer");
        System.out.println("Please enter the form below");
        System.out.println("----------");
        System.out.println("Select 1 to create specialty");
        System.out.println("Select 2 to show all specialty");
        System.out.println("Select 3 to update specialty:");
        System.out.println("Select 4 to delete specialty:");
        System.out.println("Select 5 to return");
        System.out.println("----------");
    }
}
