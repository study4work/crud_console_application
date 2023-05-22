package com.lysenko.crudapp.repository.jdbc;

import com.lysenko.crudapp.configuration.JDBCConnection;
import com.lysenko.crudapp.model.Specialty;
import com.lysenko.crudapp.model.Status;
import com.lysenko.crudapp.repository.SpecialtyRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.lysenko.crudapp.configuration.JDBCConnection.preparedStatement;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {

    @Override
    public Specialty save(Specialty specialty) {
        try {
            String SQL = "INSERT INTO specialty(status, specialty_description) " +
                "values (?, ?)";
            PreparedStatement statement = JDBCConnection.preparedStatementWithKeys(SQL);
            statement.setString(1, specialty.getStatus().name());
            statement.setString(2, specialty.getDescriptionSpecialty());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long specialtyId = generatedKeys.getLong(1);
                specialty.setId(specialtyId);
            }
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
            PreparedStatement statement = preparedStatement(SQL);
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
            String SQL = "update specialty " +
                "set status = ?, specialty_description = ? " +
                "where specialty_id = ?";
            PreparedStatement statement = preparedStatement(SQL);
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
            PreparedStatement statement = preparedStatement(SQL);
            ResultSet myRs = statement.executeQuery();
            while (myRs.next()) {
                Specialty specialty = new Specialty();
                specialty.setId(myRs.getInt(1));
                if (myRs.getString(3).equals("ACTIVE")) {
                    specialty.setStatus(Status.ACTIVE);
                } else {
                    specialty.setStatus(Status.DELETED);
                }
                specialty.setDescriptionSpecialty(myRs.getString(2));
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
            PreparedStatement statement = preparedStatement(SQL);
            statement.setInt(1, id.intValue());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void assignedSpecialtyToDeveloper(long specialtyId, long developerId) {
        try {
            String SQL = "update developer " +
             "set specialty_id = ? " +
                "where developer_id = ? ";
            PreparedStatement statement = preparedStatement(SQL);
            statement.setLong(1, specialtyId);
            statement.setLong(2, developerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
