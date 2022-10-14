package controller;

import model.Developer;
import model.Status;
import repository.GsonDeveloperRepositoryImpl;

import java.io.FileNotFoundException;
import java.util.List;

public class DeveloperController {

    private SkillsController skill;
    private SpecialtyController specialty;
    private GsonDeveloperRepositoryImpl repository;

    public DeveloperController() {
        this.skill = new SkillsController();
        this.specialty = new SpecialtyController();
        this.repository = new GsonDeveloperRepositoryImpl();
    }

    public void createDeveloper(String firstName, String lastName) {
        Developer developer = new Developer();
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setStatus(Status.ACTIVE);
        developer.setSkills(skill.findAll());
        developer.setSpecialty(specialty.find());
        repository.save(developer);
    }

    public Developer findById(Long id) throws FileNotFoundException {
        return repository.findById(id);
    }

    public List<Developer> findAll() {
        return repository.findAll();
    }

    public void delete(Long id) throws FileNotFoundException {
        repository.delete(id);
    }

    public void update(Developer developer) throws FileNotFoundException {
        repository.update(developer);
    }
}
