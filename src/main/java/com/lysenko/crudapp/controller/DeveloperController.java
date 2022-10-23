package com.lysenko.crudapp.controller;

import com.lysenko.crudapp.model.Developer;
import com.lysenko.crudapp.model.Status;
import com.lysenko.crudapp.repository.impl.GsonDeveloperRepositoryImpl;

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

    public Developer findById(Long id)  {
        return repository.findById(id);
    }

    public List<Developer> findAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public void update(Developer developer) {
        repository.update(developer);
    }
}
