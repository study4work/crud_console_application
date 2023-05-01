package com.lysenko.crudapp.controller;

import com.lysenko.crudapp.jdbcRepository.DeveloperRepository;
import com.lysenko.crudapp.jdbcRepository.impl.DeveloperRepositoryImpl;
import com.lysenko.crudapp.model.Developer;
import com.lysenko.crudapp.model.Specialty;
import com.lysenko.crudapp.model.Status;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DeveloperController {

    private final DeveloperRepository repository;

    public DeveloperController(Connection connection) {
        this.repository = new DeveloperRepositoryImpl(connection);
    }

    public void createDeveloper(String firstName, String lastName) {
        Developer developer = new Developer();
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setStatus(Status.ACTIVE);
        developer.setSkills(new ArrayList<>());
        developer.setSpecialty(new Specialty());
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
