package model;

public class Skill {
    private long id;
    private String skillDescription;
    private Status status;

    public Skill() {
    }

    public Skill(long id, String skillDescription, Status status) {
        this.id = id;
        this.skillDescription = skillDescription;
        this.status = status;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSkillDescription() {
        return this.skillDescription;
    }

    public void setSkillDescription(String skillDescription) {
        this.skillDescription = skillDescription;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String toString() {
        return "Skill{id=" + this.id + ", skillDescription='" + this.skillDescription + '\'' + ", status=" + this.status + '}';
    }
}