package view;

import controller.SkillsController;
import model.Skill;
import model.Status;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class SkillView {

    private final Scanner scanner;
    private final SkillsController skillsController;

    public SkillView() {
        this.scanner = new Scanner(System.in);
        skillsController = new SkillsController();
    }

    public void createSkill() throws FileNotFoundException {
        printSkillMenu();
        MainView view = new MainView();
        switch (scanner.next()) {
            case "1":
                System.out.println("Enter description");
                String description = scanner.next();
                skillsController.createTheSkill(description);
                System.out.println("Skill created");
                createSkill();
            case "2":
                System.out.println("Enter id to find skill: ");
                long idToFind = scanner.nextLong();
                System.out.println("Your skill: " + skillsController.findById(idToFind));
                createSkill();
            case "3":
                System.out.println(skillsController.findAll());
                createSkill();
            case "4":
                System.out.println("1. Enter old id to skill for update: ");
                long id = scanner.nextLong();
                System.out.println("2. Enter description");
                String updateDescription = scanner.next();
                Skill updateSkill = new Skill(id, updateDescription, Status.ACTIVE);
                skillsController.update(updateSkill);
                System.out.println("Skill have been updated");
                createSkill();
            case "5":
                System.out.println("Enter id to find skill: ");
                long idToDelete = scanner.nextLong();
                skillsController.delete(idToDelete);
                System.out.println("Skill have been deleted");
                createSkill();
            case "6":
                view.run();
        }
    }

    private void printSkillMenu() {
        System.out.println("----------");
        System.out.println("We need to make skills for our developer");
        System.out.println("Please enter the form below");
        System.out.println("----------");
        System.out.println("Select 1 to create skill");
        System.out.println("Select 2 to find skill by id : insert id :");
        System.out.println("Select 3 to show all skills");
        System.out.println("Select 4 to update skill by id: insert id");
        System.out.println("Select 5 to delete skill by id: insert id");
        System.out.println("Select 6 to return");
        System.out.println("----------");
    }
}
