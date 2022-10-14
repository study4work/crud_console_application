package controller;


import java.io.FileNotFoundException;
import java.util.List;
import model.Skill;
import model.Status;
import repository.GsonSkillRepositoryImpl;

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

    public Skill findById(Long id) throws FileNotFoundException {
        return this.repository.findById(id);
    }

    public void update(Skill skill) throws FileNotFoundException {
        this.repository.update(skill);
    }

    public void delete(Long id) throws FileNotFoundException {
        this.repository.delete(id);
    }
}
