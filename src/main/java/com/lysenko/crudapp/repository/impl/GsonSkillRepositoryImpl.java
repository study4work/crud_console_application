package com.lysenko.crudapp.repository.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lysenko.crudapp.model.Skill;
import com.lysenko.crudapp.model.Status;
import com.lysenko.crudapp.repository.SkillRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GsonSkillRepositoryImpl implements SkillRepository {

    private final Gson gson = new Gson();
    private final String SKILLS_FILE_NAME = "skills.json";


    private List<Skill> getAllSkillsInternal() {
        try (Reader reader = Files.newBufferedReader(Paths.get(SKILLS_FILE_NAME))) {
            return gson.fromJson(reader, new TypeToken<List<Skill>>() {}.getType());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    private void writeSkillsToFile(List<Skill> skills) {
        try (FileWriter fileWriter = new FileWriter((SKILLS_FILE_NAME))){
            gson.toJson(skills, fileWriter);
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private long generateNewId(List<Skill> skills) {
        Skill skillWithMaxId = skills.stream().max(Comparator.comparing(Skill::getId)).orElse(null);
        return Objects.nonNull(skillWithMaxId) ? skillWithMaxId.getId() + 1 : 1L;
    }

    @Override
    public Skill save(Skill skillToSave) {
        List<Skill> existingSkillsInFile = getAllSkillsInternal();
        long newId = generateNewId(existingSkillsInFile);
        skillToSave.setId(newId);
        existingSkillsInFile.add(skillToSave);
        writeSkillsToFile(existingSkillsInFile);
        return skillToSave;
    }

    @Override
    public Skill findById(Long id) {
        return getAllSkillsInternal().stream()
                .filter(s -> s.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public List<Skill> findAll() {
        return getAllSkillsInternal();
    }

    @Override
    public void delete(Long id)  {
        List<Skill> existingSkillsInFile = getAllSkillsInternal();
        existingSkillsInFile.forEach(s -> {
            if(s.getId() == id) {
                s.setStatus(Status.DELETED);
            }
        });
        writeSkillsToFile(existingSkillsInFile);
    }

    @Override
    public Skill update(Skill skill) {
        List<Skill> existingSkillsInFile = getAllSkillsInternal();
        existingSkillsInFile.forEach(existingSkill -> {
            if(existingSkill.getId() == skill.getId()) {
                existingSkill.setSkillDescription(skill.getSkillDescription());
                existingSkill.setStatus(skill.getStatus());
            }
        });
        writeSkillsToFile(existingSkillsInFile);
        return skill;
    }
}
