package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Skill;
import model.Status;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GsonSkillRepositoryImpl implements SkillRepository{

    private final Gson gson = new Gson();

    @Override
    public void save(Skill skill) {
        List<Skill> skillList;
        if (findAll().isEmpty()) {
            skillList = new ArrayList<>();
            skill.setId(1);
        } else {
            skillList = findAll();
            Skill skill1 = skillList.stream().max(Comparator.comparing(Skill::getId)).get();
            skill.setId(skill1.getId() + 1);
        }
        skillList.add(skill);
        try (FileWriter fileWriter = new FileWriter(("skills.json"))){
            gson.toJson(skillList, fileWriter);
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public Skill findById(Long id) throws FileNotFoundException {
        Skill skill = new Skill();
        List<Skill> skillList  = findAll();
        for (Skill skil : skillList) {
            if (skil.getId() == id) {
                skill = skil;
            }
        }
        return skill;
    }

    @Override
    public List<Skill> findAll() {
        List<Skill> skillList = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get("skills.json"))) {
            skillList = gson.fromJson(reader, new TypeToken<List<Skill>>() {}.getType());
        } catch (IOException e) {
            System.out.println(e);
        }
        return skillList;
    }

    @Override
    public void delete(Long id) throws FileNotFoundException {
        Skill skill = new Skill();
        skill.setId(id);
        skill.setStatus(Status.DELETED);
        update(skill);
    }

    @Override
    public void update(Skill skill) throws FileNotFoundException {
        List<Skill> skillList = findAll();
        skillList.set(skillList.indexOf(findById(skill.getId())),
                skill);
        try (FileWriter fileWriter = new FileWriter(("skills.json"))){
            gson.toJson(skillList, fileWriter);
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
