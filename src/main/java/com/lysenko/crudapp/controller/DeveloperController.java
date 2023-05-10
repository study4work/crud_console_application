package com.lysenko.crudapp.controller;

import com.lysenko.crudapp.jdbcRepository.impl.DeveloperRepositoryImpl;
import com.lysenko.crudapp.model.Developer;
import com.lysenko.crudapp.model.Status;

import java.util.List;

public class DeveloperController {

    private final DeveloperRepositoryImpl repository;

    public DeveloperController(DeveloperRepositoryImpl repository) {
        this.repository = repository;
    }

    public Developer createDeveloper(String firstName, String lastName) {
        Developer developer = new Developer();
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setStatus(Status.ACTIVE);
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
