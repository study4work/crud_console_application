package repository;

import com.google.gson.Gson;
import model.Specialty;
import model.Status;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GsonSpecilatyRepositoryImpl implements SpecialtyRepository<Specialty> {

    Gson gson = new Gson();

    @Override
    public void save(Specialty specialty) {
        specialty.setId(1);
        try (FileWriter fileWriter = new FileWriter(("specialty.json"))) {
            gson.toJson(specialty, fileWriter);
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public Specialty find() {
        Specialty specialty = new Specialty();
        try (Reader reader = Files.newBufferedReader(Paths.get("specialty.json"))) {
            specialty = gson.fromJson(reader,Specialty.class);
        } catch (IOException e) {
            System.out.println(e);
        }
        return specialty;
    }

    @Override
    public void update(Specialty specialty) {
        save(specialty);
    }

    @Override
    public void delete() {
        Specialty specialty = find();
        specialty.setStatus(Status.DELETED);
        save(specialty);
    }
}
