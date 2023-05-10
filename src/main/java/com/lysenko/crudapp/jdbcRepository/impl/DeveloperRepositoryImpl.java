package com.lysenko.crudapp.jdbcRepository.impl;

import com.lysenko.crudapp.jdbcRepository.DeveloperRepository;
import com.lysenko.crudapp.model.Developer;
import com.lysenko.crudapp.model.Skill;
import com.lysenko.crudapp.model.Specialty;
import com.lysenko.crudapp.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperRepositoryImpl implements DeveloperRepository {

    private final Connection connection;

    public DeveloperRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Developer save(Developer developer) {
        try {
            String SQL = "INSERT INTO developer(first_name, last_name, status) " +
                "values (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setString(1, developer.getFirstName());
            statement.setString(2, developer.getLastName());
            statement.setString(3, developer.getStatus().name());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Can't save developer " + e);
        }
        return developer;
    }

    @Override
    public Developer findById(Long id) {
        Developer developer = new Developer();
        Specialty specialty = new Specialty();
        developer.setSpecialty(specialty);
        try  {
            String SQL = "select d.*, s.*  from developer as d " +
                "join specialty s on d.specialty_id = s.specialty_id " +
                "where developer_id = ? ";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, id.intValue());
            ResultSet myRs = statement.executeQuery();
            while (myRs.next()) {
                developer.setId(myRs.getInt("developer_id"));
                developer.setFirstName(myRs.getString("first_name"));
                developer.setLastName(myRs.getString("last_name"));
                developer.setSkills(getSkillAssignedToDeveloper(id.intValue()));
                if(myRs.getString("d.status").equals("ACTIVE")) {
                    developer.setStatus(Status.ACTIVE);
                } else {
                    developer.setStatus(Status.DELETED);
                }

                specialty.setId(myRs.getInt("s.specialty_id"));
                specialty.setDescriptionSpecialty(myRs.getString("s.specialty_description"));
                if(myRs.getString("s.status").equals("ACTIVE")) {
                    specialty.setStatus(Status.ACTIVE);
                } else {
                    specialty.setStatus(Status.DELETED);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't find Developer by id: " + id + e);
        }
        return developer;
    }

    @Override
    public List<Developer> findAll() {
        List<Developer> list = new ArrayList<>();
        try {
            String SQL = "Select * from developer";
            PreparedStatement statement = connection.prepareStatement(SQL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Developer developer = new Developer();
                developer.setId(rs.getInt(1));
                developer.setSpecialty(getSpecialtyAssigneeToDeveloper(rs.getInt("specialty_id")));
                developer.setFirstName(rs.getString(3));
                developer.setLastName(rs.getString(4));
                if(rs.getString(5).equals("ACTIVE")) {
                    developer.setStatus(Status.ACTIVE);
                } else {
                    developer.setStatus(Status.DELETED);
                }
                developer.setSkills(getSkillAssignedToDeveloper((int)developer.getId()));
                list.add(developer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't find all developers in db " + e);
        }
        return list;
    }

    @Override
    public void delete(Long id) {
        try {
            String SQL = "update developer " +
                "set status = ? " +
                "where developer_id = ?";
            PreparedStatement statment = connection.prepareStatement(SQL);
            statment.setString(1, "DELETE");
            statment.setInt(2, id.intValue());
            statment.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete developer with id " + id);
        }
    }

    @Override
    public Developer update(Developer developer) {
        try {
            String SQL = "update developer " +
                "set first_name = ?, last_name = ?" +
                "where developer_id = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setString(1, developer.getFirstName());
            statement.setString(2, developer.getLastName());
            statement.setInt(3, (int) developer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't update developer with id: " + developer.getId());
        }
        return developer;
    }

    private List<Skill> getSkillAssignedToDeveloper(int developerId) {
        List<Skill> skillList = new ArrayList<>();
        try  {
            String SQL = "select * from skill " +
                "left join developer_skill ds on skill.skill_id = ds.skill_id " +
                "where ds.developer_id = ? ";
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
            throw new RuntimeException("Can't get skills for developer with id: " + developerId);
        }
        return skillList;
    }

    private Specialty getSpecialtyAssigneeToDeveloper(int specialtyId) {
        Specialty specialty = new Specialty();
        try {
            String SQL = "select * from specialty " +
                "where specialty_id = ? ";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, specialtyId);
            ResultSet myRs = statement.executeQuery();
            while (myRs.next()) {
                specialty.setId(myRs.getInt(1));
                specialty.setDescriptionSpecialty(myRs.getString(2));
                if (myRs.getString(3).equals("ACTIVE")) {
                    specialty.setStatus(Status.ACTIVE);
                } else {
                    specialty.setStatus(Status.DELETED);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specialty;
    }
}
