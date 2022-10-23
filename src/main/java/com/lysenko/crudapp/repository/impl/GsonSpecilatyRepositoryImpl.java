package com.lysenko.crudapp.repository.impl;

import com.google.gson.Gson;
import com.lysenko.crudapp.model.Specialty;
import com.lysenko.crudapp.model.Status;
import com.lysenko.crudapp.repository.SpecialtyRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GsonSpecilatyRepositoryImpl implements SpecialtyRepository<Specialty> {

    Gson gson = new Gson();
    private final String SPECIALTY_FILE_NAME = "specialty.json";

    private Specialty getSpecialtyInternal() {
        try (Reader reader = Files.newBufferedReader(Paths.get(SPECIALTY_FILE_NAME))) {
            return  gson.fromJson(reader,Specialty.class);
        } catch (IOException e) {
            System.out.println(e);
            return new Specialty();
        }
    }

    private void writeSpecialtyToFile(Specialty specialty) {
        try (FileWriter fileWriter = new FileWriter((SPECIALTY_FILE_NAME))){
            gson.toJson(specialty, fileWriter);
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(Specialty specialty) {
        specialty.setId(1);
        writeSpecialtyToFile(specialty);
    }

    @Override
    public Specialty find() {
        return getSpecialtyInternal();
    }

    @Override
    public void update(Specialty specialty) {
        writeSpecialtyToFile(specialty);
    }

    @Override
    public void delete() {
        Specialty specialty = getSpecialtyInternal();
        if (specialty != null) {
            specialty.setStatus(Status.DELETED);
        }
        writeSpecialtyToFile(specialty);
    }
}
