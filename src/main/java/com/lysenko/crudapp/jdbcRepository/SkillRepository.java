package com.lysenko.crudapp.jdbcRepository;

import com.lysenko.crudapp.model.Skill;

import java.util.List;

public interface SkillRepository extends GenericRepository<Skill,Long> {

    List<Skill> getSkillAssignedToDeveloper(int developerID);

    void assigneedSkillToDeveloper(int developerId, int skillId);
}
