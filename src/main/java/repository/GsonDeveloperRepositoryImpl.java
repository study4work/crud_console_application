package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Developer;
import model.Status;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GsonDeveloperRepositoryImpl implements DeveloperRepository {

    private final Gson gson = new Gson();

    public void save(Developer developer) {
        List<Developer> developerList;
        if(findAll().isEmpty()) {
            developerList = new ArrayList<>();
            developer.setId(1);
        } else {
            developerList = findAll();
            Developer developer1 = developerList.stream().max(Comparator.comparing(Developer::getId)).get();
            developer.setId(developer1.getId() + 1);
        }
        developerList.add(developer);
        try (FileWriter fileWriter = new FileWriter(("developer.json"))){
            gson.toJson(developerList, fileWriter);
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public Developer findById(Long id) throws FileNotFoundException {
        Developer developer = new Developer();
        List<Developer> developerList  = findAll();
        for (Developer developer1 : developerList) {
            if (developer1.getId() == id) {
                developer = developer1;
            }
        }
        return developer;
    }

    public List<Developer> findAll() {
        List<Developer> developerList = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get("developer.json"))) {
            developerList = gson.fromJson(reader, new TypeToken<List<Developer>>() {}.getType());
        } catch (IOException e) {
            System.out.println(e);
        }
        return developerList;
    }

    public void delete(Long id) throws FileNotFoundException {
        Developer developer = findById(id);
        developer.setStatus(Status.DELETED);
        save(developer);
    }

    public void update(Developer developer) throws FileNotFoundException {
        List<Developer> developerList = findAll();
        developerList.set(developerList.indexOf(findById(developer.getId())),
                developer);
        try (FileWriter fileWriter = new FileWriter(("developer.json"))){
            gson.toJson(developerList, fileWriter);
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}


