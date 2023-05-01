package com.lysenko.crudapp.controller;


import com.lysenko.crudapp.jdbcRepository.impl.SkillRepositoryImpl;
import com.lysenko.crudapp.model.Skill;
import com.lysenko.crudapp.model.Status;

import java.sql.Connection;
import java.util.List;

public class SkillsController {
    private final SkillRepositoryImpl skillRepository;

    public SkillsController(Connection connection) {
        this.skillRepository = new SkillRepositoryImpl(connection);
    }

    public void createTheSkill(String description) {
        Skill skill = new Skill();
        skill.setSkillDescription(description);
        skill.setStatus(Status.ACTIVE);
        skillRepository.save(skill);
    }

    public List<Skill> findAll() {
        return this.skillRepository.findAll();
    }

    public Skill findById(Long id) {
        return this.skillRepository.findById(id);
    }

    public void update(Skill skill) {
        this.skillRepository.update(skill);
    }

    public void delete(Long id) {
        this.skillRepository.delete(id);
    }

    public void assineSkilltoDeveloper(int developerId, int skilId) {
        skillRepository.assigneedSkillToDeveloper(developerId, skilId);
    }
}
