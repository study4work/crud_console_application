package com.lysenko.crudapp.repository.jdbc;

import com.lysenko.crudapp.configuration.JDBCConnection;
import com.lysenko.crudapp.model.Developer;
import com.lysenko.crudapp.model.Skill;
import com.lysenko.crudapp.model.Specialty;
import com.lysenko.crudapp.repository.DeveloperRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.lysenko.crudapp.configuration.JDBCConnection.preparedStatement;
import static com.lysenko.crudapp.utill.ConverterUtil.setStatus;

public class DeveloperRepositoryImpl implements DeveloperRepository {

    private final SpecialtyRepositoryImpl specialtyRepository;
    private final SkillRepositoryImpl skillRepository;

    public DeveloperRepositoryImpl(SpecialtyRepositoryImpl specialtyRepository, SkillRepositoryImpl skillRepository) {
        this.specialtyRepository = specialtyRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public Developer save(Developer developer) {
        try {
            //language=MySQL
            String InsertDeveloper = "Insert Into developer(first_name, last_name, status) "
                + "values (?, ?, ?); ";
            PreparedStatement statement = JDBCConnection.preparedStatementWithKeys(InsertDeveloper);
            statement.setString(1, developer.getFirstName());
            statement.setString(2, developer.getLastName());
            statement.setString(3, developer.getStatus().name());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long developerId = generatedKeys.getLong(1);
                developer.setId(developerId);
            }

            if (developer.getSpecialty() != null) {
                Specialty specialty = specialtyRepository.save(developer.getSpecialty());
                specialtyRepository.assignedSpecialtyToDeveloper(specialty.getId(), developer.getId());
            }

            List<Skill> skillList = developer.getSkills().isEmpty() ? null : developer.getSkills();
            if (skillList != null) {
                skillList.forEach(skill -> {
                    skillRepository.save(skill);
                    skillRepository.assignedSkillToDeveloper(developer.getId(), skill.getId());
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't save developer " + e);
        }
        return developer;
    }

    @Override
    public Developer findById(Long id) {
        Developer developer;
        try  {
            //language=MySQL
            String SQL = "select s.*, d.*, sk.* from skill sk "
                + "join developer_skill ds on sk.skill_id = ds.skill_id "
                + "join developer d on ds.developer_id = d.developer_id "
                + "join specialty s on d.specialty_id = s.specialty_id "
                + "where ds.developer_id = ? ";
            PreparedStatement statement = preparedStatement(SQL);
            statement.setInt(1, id.intValue());
            ResultSet myRs = statement.executeQuery();
            developer = mapResultSetToDeveloper(myRs).get(0);
        } catch (SQLException e) {
            throw new RuntimeException("Can't find Developer by id: " + id + e);
        }
        return developer;
    }

    @Override
    public List<Developer> findAll() {
        List<Developer> list;
        try {
            //language=MySQL
            String SQL = "select s.*, d.*, sk.* from skill sk "
                + "join developer_skill ds on sk.skill_id = ds.skill_id "
                + "join developer d on ds.developer_id = d.developer_id "
                + "join specialty s on d.specialty_id = s.specialty_id ";
            PreparedStatement statement = preparedStatement(SQL);
            ResultSet myRs = statement.executeQuery();
              list = mapResultSetToDeveloper(myRs);

        } catch (SQLException e) {
            throw new RuntimeException("Can't find all developers in db " + e);
        }
        return list;
    }

    @Override
    public void delete(Long id) {
        try {
            //language=MySQL
            String SQL = "update developer " +
                "set status = ? " +
                "where developer_id = ?";
            PreparedStatement statment = preparedStatement(SQL);
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
            //language=MySQL
            String SQL = "update developer " +
                "set first_name = ?, last_name = ?" +
                "where developer_id = ?";
            PreparedStatement statement = preparedStatement(SQL);
            statement.setString(1, developer.getFirstName());
            statement.setString(2, developer.getLastName());
            statement.setInt(3, (int) developer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't update developer with id: " + developer.getId());
        }
        return developer;
    }

    private List<Developer> mapResultSetToDeveloper(ResultSet myRs) throws SQLException {
        List<Developer> developers = new ArrayList<>();
        Developer developer = new Developer();
        List<Skill> skillList = new ArrayList<>();
        while (myRs.next()) {
            if (developer.getId() != myRs.getInt("developer_id")) {
                developer = new Developer();
                Specialty specialty = new Specialty();
                developer.setId(myRs.getInt("developer_id"));
                developer.setFirstName(myRs.getString("first_name"));
                developer.setLastName(myRs.getString("last_name"));
                developer.setStatus(setStatus(myRs.getString("d.status")));

                specialty.setId(myRs.getInt("specialty_id"));
                specialty.setDescriptionSpecialty(myRs.getString("specialty_description"));
                specialty.setStatus(setStatus(myRs.getString("s.status")));

                skillList = new ArrayList<>();
                developer.setSpecialty(specialty);
                developer.setSkills(skillList);
                developers.add(developer);
            }

            Skill skill = new Skill();
            skill.setId(myRs.getInt("skill_id"));
            skill.setSkillDescription(myRs.getString("skill_description"));
            skill.setStatus(setStatus(myRs.getString("sk.status")));
            skillList.add(skill);
        }
        return developers;
    }
}
