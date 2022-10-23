package com.lysenko.crudapp.repository.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lysenko.crudapp.model.Developer;
import com.lysenko.crudapp.model.Status;
import com.lysenko.crudapp.repository.DeveloperRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GsonDeveloperRepositoryImpl implements DeveloperRepository {

    private final Gson gson = new Gson();
    private final String DEVELOPER_FILE_NAME = "developers.json";

    private List<Developer> getAllDevelopersInternal() {
        try (Reader reader = Files.newBufferedReader(Paths.get(DEVELOPER_FILE_NAME))) {
            return gson.fromJson(reader, new TypeToken<List<Developer>>() {}.getType());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    private void writeDevelopersToFile(List<Developer> developers) {
        try (FileWriter fileWriter = new FileWriter((DEVELOPER_FILE_NAME))){
            gson.toJson(developers, fileWriter);
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private long generateNewId(List<Developer> developers) {
        Developer developerWithMaxId = developers.stream().max(Comparator.comparing(Developer::getId)).orElse(null);
        return Objects.nonNull(developerWithMaxId) ? developerWithMaxId.getId() + 1 : 1L;
    }

    public Developer save(Developer developerToSave) {
        List<Developer> existingDevelopersInFile = getAllDevelopersInternal();
        long newId = generateNewId(existingDevelopersInFile);
        developerToSave.setId(newId);
        existingDevelopersInFile.add(developerToSave);
        writeDevelopersToFile(existingDevelopersInFile);
        return developerToSave;
    }

    public Developer findById(Long id)  {
        return getAllDevelopersInternal().stream()
                .filter(s -> s.getId() == id)
                .findFirst().orElse(null);
    }

    public List<Developer> findAll() {
        return getAllDevelopersInternal();
    }

    public void delete(Long id) {
        List<Developer> existingDeveloperInFile = getAllDevelopersInternal();
        existingDeveloperInFile.forEach(s -> {
            if(s.getId() == id) {
                s.setStatus(Status.DELETED);
            }
        });
        writeDevelopersToFile(existingDeveloperInFile);
    }

    public Developer update(Developer developer) {
        List<Developer> existingDevelopersInFile = getAllDevelopersInternal();
        existingDevelopersInFile.forEach(existingDeveloper -> {
            if(existingDeveloper.getId() == developer.getId()) {
                existingDeveloper.setSkills(developer.getSkills());
                existingDeveloper.setSpecialty(developer.getSpecialty());
                existingDeveloper.setStatus(developer.getStatus());
                existingDeveloper.setFirstName(developer.getFirstName());
                existingDeveloper.setLastName(developer.getLastName());
            }
        });
        writeDevelopersToFile(existingDevelopersInFile);
        return developer;
    }
}


