package com.lysenko.crudapp.controller;


import com.lysenko.crudapp.jdbcRepository.impl.SpecialtyRepositoryImpl;
import com.lysenko.crudapp.model.Specialty;
import com.lysenko.crudapp.model.Status;

import java.util.List;

public class SpecialtyController {
    private final SpecialtyRepositoryImpl repository;

    public SpecialtyController(SpecialtyRepositoryImpl repository) {
        this.repository = repository;
    }

    public Specialty createSpecialty(String description) {
        Specialty specialty = new Specialty();
        specialty.setDescriptionSpecialty(description);
        specialty.setStatus(Status.ACTIVE);
        return this.repository.save(specialty);
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

    public void setSpecialtyToDeveloper(long specialty_id, long developer_id) {
        this.repository.assignedSpecialtyToDeveloper((int)specialty_id, (int)developer_id);
    }
}