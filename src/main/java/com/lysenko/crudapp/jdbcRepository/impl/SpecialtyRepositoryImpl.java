package com.lysenko.crudapp.jdbcRepository.impl;

import com.lysenko.crudapp.jdbcRepository.SpecialtyRepository;
import com.lysenko.crudapp.model.Specialty;
import com.lysenko.crudapp.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {

    private final Connection connection;

    public SpecialtyRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Specialty save(Specialty specialty) {
        try {
            String SQL = "INSERT INTO specialty(status, descriptionSpecialty) " +
                "values (?, ?)";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setString(1, specialty.getStatus().name());
            statement.setString(2, specialty.getDescriptionSpecialty());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specialty;
    }

    @Override
    public Specialty findById(Long id) {
        Specialty specialty = new Specialty();
        try {
            String SQL = "select * from specialty " +
                "where specialty_id = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, id.intValue());
            ResultSet myRs = statement.executeQuery();
            while (myRs.next()) {
                specialty.setId(myRs.getInt(1));
                if (myRs.getString(2).equals("ACTIVE")) {
                    specialty.setStatus(Status.ACTIVE);
                } else {
                    specialty.setStatus(Status.DELETED);
                }
                specialty.setDescriptionSpecialty(myRs.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specialty;
    }

    @Override
    public Specialty update(Specialty specialty) {
        try  {
            String SQL = "update crud_console_application.specialty " +
                "set status = ?, descriptionSpecialty = ? " +
                "where specialty_id = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setString(1, specialty.getStatus().name());
            statement.setString(2, specialty.getDescriptionSpecialty());
            statement.setInt(3, (int)specialty.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specialty;
    }

    @Override
    public List<Specialty> findAll() {
        List<Specialty> specialtyList = new ArrayList<>();
        try  {
            String SQL = "select * from specialty ";
            PreparedStatement statement = connection.prepareStatement(SQL);
            ResultSet myRs = statement.executeQuery();
            while (myRs.next()) {
                Specialty specialty = new Specialty();
                specialty.setId(myRs.getInt(1));
                if (myRs.getString(2).equals("ACTIVE")) {
                    specialty.setStatus(Status.ACTIVE);
                } else {
                    specialty.setStatus(Status.DELETED);
                }
                specialty.setDescriptionSpecialty(myRs.getString(3));
                specialtyList.add(specialty);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specialtyList;
    }

    @Override
    public void delete(Long id) {
        try  {
            String SQL = "update specialty " +
                "set status = 'DELETE' " +
                "where specialty_id = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, id.intValue());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
