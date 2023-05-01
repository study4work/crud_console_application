package com.lysenko.crudapp.controller;


import com.lysenko.crudapp.configuration.JDBCConnection;
import com.lysenko.crudapp.jdbcRepository.SpecialtyRepository;
import com.lysenko.crudapp.jdbcRepository.impl.SpecialtyRepositoryImpl;
import com.lysenko.crudapp.model.Specialty;
import com.lysenko.crudapp.model.Status;

import java.sql.Connection;
import java.util.List;

public class SpecialtyController {
    private final SpecialtyRepository repository;

    public SpecialtyController(Connection connection) {
        this.repository = new SpecialtyRepositoryImpl(connection);
    }

    public void createTheSpecilaty(String description) {
        Specialty specialty = new Specialty();
        specialty.setDescriptionSpecialty(description);
        specialty.setStatus(Status.ACTIVE);
        this.repository.save(specialty);
    }

    public Specialty find(Long id) {
        return this.repository.findById(id);
    }

    public List<Specialty> findAll() {
        return this.repository.findAll();
    }

    public void delete(Long id) {
        this.repository.delete(id);
    }

    public void update(Specialty specialty) {
        this.repository.update(specialty);
    }
}