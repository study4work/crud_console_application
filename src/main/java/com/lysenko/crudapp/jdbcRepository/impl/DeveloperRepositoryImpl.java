package com.lysenko.crudapp.jdbcRepository.impl;

import com.lysenko.crudapp.jdbcRepository.DeveloperRepository;
import com.lysenko.crudapp.jdbcRepository.SkillRepository;
import com.lysenko.crudapp.model.Developer;
import com.lysenko.crudapp.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperRepositoryImpl implements DeveloperRepository {

    private final Connection connection;
    private final SpecialtyRepositoryImpl specialtyRepository;
    private final SkillRepository skillRepository;

    public DeveloperRepositoryImpl(Connection connection) {
        this.connection = connection;
        this.specialtyRepository = new SpecialtyRepositoryImpl(connection);
        this.skillRepository = new SkillRepositoryImpl(connection);
    }

    @Override
    public Developer save(Developer developer) {
        try {
            String SQL = "INSERT INTO developer(specialty_id, firstName, lastName, status) " +
                "values (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, (int)developer.getSpecialty().getId());
            statement.setString(2, developer.getFirstName());
            statement.setString(3, developer.getLastName());
            statement.setString(4, developer.getStatus().name());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return developer;
    }

    @Override
    public Developer findById(Long id) {
        Developer developer = new Developer();
        try  {
            String SQL = "select * from developer " +
                "where developer_id = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, id.intValue());
            ResultSet myRs = statement.executeQuery();
            while (myRs.next()) {
                developer.setId(myRs.getInt(1));
                developer.setSpecialty(specialtyRepository.findById((long)myRs.getInt(2)));
                developer.setFirstName(myRs.getString(3));
                developer.setLastName(myRs.getString(4));
                developer.setSkills(new ArrayList<>(skillRepository.getSkillAssignedToDeveloper(id.intValue())));
                if(myRs.getString(5).equals("ACTIVE")) {
                    developer.setStatus(Status.ACTIVE);
                } else {
                    developer.setStatus(Status.DELETED);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                developer.setSpecialty(specialtyRepository.findById(developer.getId()));
                developer.setFirstName(rs.getString(3));
                developer.setLastName(rs.getString(4));
                if(rs.getString(5).equals("ACTIVE")) {
                    developer.setStatus(Status.ACTIVE);
                } else {
                    developer.setStatus(Status.DELETED);
                }
                developer.setSkills(skillRepository.getSkillAssignedToDeveloper((int)developer.getId()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public Developer update(Developer developer) {
        try {
            String SQL = "update developer " +
                "set specialty_id = ?, firstName = ?, lastName = ?, status = ?" +
                "where developer_id = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, (int) developer.getSpecialty().getId());
            statement.setString(2, developer.getFirstName());
            statement.setString(3, developer.getLastName());
            statement.setString(4, developer.getStatus().name());
            statement.setInt(5, (int) developer.getId());
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
