package view;

import controller.SpecialtyController;
import model.Specialty;
import model.Status;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class SpecialtyView {

    private final Scanner scanner;
    private final SpecialtyController specialtyController;

    public SpecialtyView() {
        this.scanner = new Scanner(System.in);
        this.specialtyController = new SpecialtyController();
    }

    public void createSpecialty() throws FileNotFoundException {
        printSpecialityMenu();
        switch (scanner.next()) {
            case "1" :
                System.out.println("1. Enter specialty description");
                String description = scanner.next();
                specialtyController.createTheSpecilaty(description);
                System.out.println("Specialty created");
                createSpecialty();
            case "2":
                System.out.println(specialtyController.find());
                createSpecialty();
            case "3":
                System.out.println("Enter new description");
                String updateDescription = scanner.next();
                Specialty updateSpecialty = new Specialty(updateDescription, Status.ACTIVE);
                specialtyController.update(updateSpecialty);
                System.out.println("specialty have been updated");
                createSpecialty();
            case "4":
                specialtyController.delete();
                System.out.println("specialty have been deleted");
                createSpecialty();
            case "5":
                MainView view = new MainView();
                view.run();
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
