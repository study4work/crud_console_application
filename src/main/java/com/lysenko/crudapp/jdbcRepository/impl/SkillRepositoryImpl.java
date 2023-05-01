package com.lysenko.crudapp.jdbcRepository.impl;

import com.lysenko.crudapp.jdbcRepository.SkillRepository;
import com.lysenko.crudapp.model.Skill;
import com.lysenko.crudapp.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillRepositoryImpl implements SkillRepository {

    private final Connection connection;

    public SkillRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Skill save(Skill skill) {
        try  {
            String SQL = "insert into crud.skill (skillDescription, status) " +
                "VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setString(1, skill.getSkillDescription());
            statement.setString(2, skill.getStatus().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return skill;
    }

    @Override
    public Skill findById(Long id) {
        Skill skill = new Skill();
        try  {
            String SQL = "select * from crud.skill " +
                "where skill_id = ? ";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, id.intValue());
            ResultSet myRs = statement.executeQuery();
            while (myRs.next()) {
                skill.setId(myRs.getInt(1));
                skill.setSkillDescription(myRs.getString(2));
                if (myRs.getString(3).equals("ACTIVE")) {
                    skill.setStatus(Status.ACTIVE);
                } else {
                    skill.setStatus(Status.DELETED);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return skill;
    }

    @Override
    public List<Skill> findAll() {
        List<Skill> skillList = new ArrayList<>();
        try  {
            String SQL = "select * from crud.skill";
            PreparedStatement statement = connection.prepareStatement(SQL);
            ResultSet myRs = statement.executeQuery();
            while (myRs.next()) {
                Skill skill = new Skill();
                skill.setId(myRs.getInt(1));
                skill.setSkillDescription(myRs.getString(2));
                if (myRs.getString(3).equals("ACTIVE")) {
                    skill.setStatus(Status.ACTIVE);
                } else {
                    skill.setStatus(Status.DELETED);
                }
                skillList.add(skill);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return skillList;
    }

    @Override
    public void delete(Long id) {
        try  {
            String SQL = "update crud.skill " +
                "set status = 'DELETE' " +
                "where skill_id = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, id.intValue());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Skill update(Skill skill) {
        try  {
            String SQL = "update crud.skill " +
                "set skillDescription = ?, status = ? " +
                "where skill_id = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setString(1, skill.getSkillDescription());
            if (skill.getStatus().equals(Status.ACTIVE)) {
                statement.setString(2, "ACTIVE");
            } else {
                statement.setString(2, "DELETE");
            }
            statement.setInt(3, (int)skill.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return skill;
    }

    public List<Skill> getSkillAssignedToDeveloper(int developerId) {
        List<Skill> skillList = new ArrayList<>();
        try  {
            String SQL = "select * from skill " +
                "left join developer_skill ds on skill.skill_id = ds.skill_id " +
                "where ds.deveoper_id = ? ";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, developerId);
            ResultSet myRs = statement.executeQuery();
            while (myRs.next()) {
                Skill skill = new Skill();
                skill.setId(myRs.getInt(1));
                skill.setSkillDescription(myRs.getString(2));
                if (myRs.getString(3).equals("ACTIVE")) {
                    skill.setStatus(Status.ACTIVE);
                } else {
                    skill.setStatus(Status.DELETED);
                }
                skillList.add(skill);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return skillList;
    }

    public void assigneedSkillToDeveloper(int developerId, int skillId) {
        try {
            String SQL = "INSERT INTO developer_skill(deveoper_id, skill_id) " +
                "values (?, ?)";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, developerId);
            statement.setInt(2, skillId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
