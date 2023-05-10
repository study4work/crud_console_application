package com.lysenko.crudapp.jdbcRepository;

import com.lysenko.crudapp.model.Skill;

public interface SkillRepository extends GenericRepository<Skill,Long> {

    void assignedSkillToDeveloper(int developerId, int skillId);
}
