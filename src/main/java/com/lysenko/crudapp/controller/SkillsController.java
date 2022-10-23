package com.lysenko.crudapp.controller;


import java.util.List;
import com.lysenko.crudapp.model.Skill;
import com.lysenko.crudapp.model.Status;
import com.lysenko.crudapp.repository.impl.GsonSkillRepositoryImpl;

public class SkillsController {
    private final GsonSkillRepositoryImpl repository = new GsonSkillRepositoryImpl();

    public SkillsController() {
    }

    public void createTheSkill(String description) {
        Skill skill = new Skill();
        skill.setSkillDescription(description);
        skill.setStatus(Status.ACTIVE);
        this.repository.save(skill);
    }

    public List<Skill> findAll() {
        return this.repository.findAll();
    }

    public Skill findById(Long id) {
        return this.repository.findById(id);
    }

    public void update(Skill skill) {
        this.repository.update(skill);
    }

    public void delete(Long id) {
        this.repository.delete(id);
    }
}
