package com.lysenko.crudapp.controller;

import com.lysenko.crudapp.repository.jdbc.SkillRepositoryImpl;
import com.lysenko.crudapp.model.Skill;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SkillsControllerTest {

    private final SkillRepositoryImpl skillRepository = mock(SkillRepositoryImpl.class);
    private final SkillsController skillsController = new SkillsController(skillRepository);

    @Test
    public void testWhenCreateSkill() {
        String description = "testDescription";
        skillsController.createTheSkill(description);
        verify(skillRepository, times(1)).save(any());
    }

    @Test
    public void testWhenFindSkillById() {
        long id = 1;
        Skill skill = new Skill();
        skill.setId(id);
        when(skillRepository.findById(id)).thenReturn(skill);
        Skill result = skillsController.findById(id);
        Assert.assertEquals(skill, result);
    }

    @Test
    public void testWhenFindAll() {
        List<Skill> skillList = new ArrayList<>();
        Skill skill = new Skill();
        skill.setSkillDescription("Desc");
        skillList.add(skill);
        when(skillRepository.findAll()).thenReturn(skillList);
        List<Skill> result = skillsController.findAll();
        Assert.assertEquals("Desc", result.get(0).getSkillDescription());
        verify(skillRepository, times(1)).findAll();
    }

    @Test
    public void testWhenUpdateSkill() {
        skillsController.update(any());
        verify(skillRepository, times(1)).update(any());
    }

    @Test
    public void testWhenDelete() {
        skillsController.delete(any());
        verify(skillRepository, times(1)).delete(any());
    }
}