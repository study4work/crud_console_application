package com.lysenko.crudapp.model;

public class Specialty {
    private long id;
    private String descriptionSpecialty;
    private Status status;

    public Specialty(String descriptionSpecialty, Status status) {
        this.descriptionSpecialty = descriptionSpecialty;
        this.status = status;
    }

    public Specialty() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescriptionSpecialty() {
        return this.descriptionSpecialty;
    }

    public void setDescriptionSpecialty(String descriptionSpecialty) {
        this.descriptionSpecialty = descriptionSpecialty;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String toString() {
        return "Specialty {id=" + this.id + ", descriptionSpecialty='" + this.descriptionSpecialty + '\'' + ", status=" + this.status + '}';
    }
}
