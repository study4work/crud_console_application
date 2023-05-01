package com.lysenko.crudapp.model;


import java.util.List;

public class Developer {
    private long id;
    private String firstName;
    private String lastName;
    private List<Skill> skills;
    private Specialty specialty;
    private Status status;

    public Developer() {
    }

    public Developer(long id, String firstName, String lastName, List<Skill> skills, Specialty specialty, Status status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.skills = skills;
        this.specialty = specialty;
        this.status = status;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Skill> getSkills() {
        return this.skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public Specialty getSpecialty() {
        return this.specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String toString() {
        return "Delveloper{id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName + '\'' + ", skills=" + this.skills + ", specialty=" + this.specialty + ", status=" + this.status + '}';
    }
}
