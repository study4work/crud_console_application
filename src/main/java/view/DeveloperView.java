package view;

import controller.DeveloperController;
import controller.SkillsController;
import controller.SpecialtyController;
import model.Developer;
import model.Skill;
import model.Specialty;
import model.Status;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class DeveloperView {

    private final Scanner scanner;
    private final DeveloperController developerController;
    private final SkillsController skillsController;
    private final SpecialtyController specialtyController;

    public DeveloperView() {
        this.scanner = new Scanner(System.in);
        this.developerController = new DeveloperController();
        this.skillsController = new SkillsController();
        this.specialtyController = new SpecialtyController();
    }

    public void createNewDeveloper() throws FileNotFoundException {
        printDeveloperMenu();
        List<Skill> skills;
        switch (scanner.next()) {
            case "1" :
                System.out.println("Enter first name:");
                String firstName = scanner.next();
                System.out.println("Enter last name:");
                String lastName = scanner.next();
                developerController.createDeveloper(firstName, lastName);
                System.out.println("Developer created");
                createNewDeveloper();
            case "2":
                System.out.println("Enter id to find developer: ");
                long idToFind = scanner.nextLong();
                System.out.println("Your developer: " + developerController.findById(idToFind));
                createNewDeveloper();
            case "3":
                System.out.print(developerController.findAll());
                createNewDeveloper();
            case "4":
                System.out.println("Enter old id fo developer need to be updated: ");
                long id = scanner.nextLong();
                System.out.println("Enter first name:");
                String newFirstName = scanner.next();
                System.out.println("Enter last name:");
                String newLastName = scanner.next();

                System.out.println("Do you need a new skills?: y/n ");
                if (scanner.next().equals("y")) {
                    System.out.println("Enter the discription of skill: ");
                    String skillDescription = scanner.next();
                    skillsController.createTheSkill(skillDescription);
                    skills = skillsController.findAll();
                } else {
                    skills = skillsController.findAll();
                }

                Specialty specialty = specialtyController.find();

                Developer updateDeveloper = new Developer(id, newFirstName, newLastName, skills, specialty, Status.ACTIVE);
                developerController.update(updateDeveloper);
                System.out.println("developer have been updated");
                createNewDeveloper();
            case "5":
                System.out.println("Enter id to find developer: ");
                long idToDelete = scanner.nextLong();
                developerController.delete(idToDelete);
                System.out.println("Developer have been deleted");
                createNewDeveloper();
            case "6":
                MainView view = new MainView();
                view.run();
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
