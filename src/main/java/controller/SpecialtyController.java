package controller;


import model.Specialty;
import model.Status;
import repository.GsonSpecilatyRepositoryImpl;

public class SpecialtyController {
    private final GsonSpecilatyRepositoryImpl repository = new GsonSpecilatyRepositoryImpl();

    public SpecialtyController() {
    }


    public void createTheSpecilaty(String description) {
        Specialty specialty = new Specialty();
        specialty.setDescriptionSpecialty(description);
        specialty.setStatus(Status.ACTIVE);
        this.repository.save(specialty);
    }

    public Specialty find() {
        return this.repository.find();
    }

    public void delete() {
        this.repository.delete();
    }

    public void update(Specialty specialty) {
        this.repository.update(specialty);
    }
}