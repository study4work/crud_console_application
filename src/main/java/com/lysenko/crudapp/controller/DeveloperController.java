package com.lysenko.crudapp.controller;

import com.lysenko.crudapp.model.Skill;
import com.lysenko.crudapp.model.Specialty;
import com.lysenko.crudapp.repository.jdbc.DeveloperRepositoryImpl;
import com.lysenko.crudapp.model.Developer;
import com.lysenko.crudapp.model.Status;

import java.util.List;

public class DeveloperController {

    private final DeveloperRepositoryImpl repository;

    public DeveloperController(DeveloperRepositoryImpl repository) {
        this.repository = repository;
    }

    public Developer createDeveloper(String firstName, String lastName, String specialtyDescription, List<Skill> skills) {
        Developer developer = new Developer();
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setStatus(Status.ACTIVE);

        Specialty specialty = new Specialty();
        specialty.setDescriptionSpecialty(specialtyDescription);
        specialty.setStatus(Status.ACTIVE);

        developer.setSpecialty(specialty);
        developer.setSkills(skills);
        return repository.save(developer);
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

    public Developer update(Developer developer) {
        return repository.update(developer);
    }
}
