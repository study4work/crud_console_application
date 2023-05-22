package com.lysenko.crudapp.repository.jdbc;

import com.lysenko.crudapp.configuration.JDBCConnection;
import com.lysenko.crudapp.model.Skill;
import com.lysenko.crudapp.repository.SkillRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.lysenko.crudapp.configuration.JDBCConnection.preparedStatement;
import static com.lysenko.crudapp.utill.ConverterUtil.setStatus;

public class SkillRepositoryImpl implements SkillRepository {

    @Override
    public Skill save(Skill skill) {
        try  {
            String SQL = "insert into crud.skill (skill_description, status) " +
                "VALUES (?, ?)";
            PreparedStatement statement = JDBCConnection.preparedStatementWithKeys(SQL);
            statement.setString(1, skill.getSkillDescription());
            statement.setString(2, skill.getStatus().name());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long skillId = generatedKeys.getLong(1);
                skill.setId(skillId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return skill;
    }

    @Override
    public Skill findById(Long id) {
        Skill skill = new Skill();
        try  {
            String SQL = "select * from skill " +
                "where skill_id = ? ";
            PreparedStatement statement = preparedStatement(SQL);
            statement.setInt(1, id.intValue());
            ResultSet myRs = statement.executeQuery();
            while (myRs.next()) {
                skill.setId(myRs.getInt(1));
                skill.setSkillDescription(myRs.getString(2));
                skill.setStatus(setStatus(myRs.getString("skill.status")));
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
            String SQL = "select * from skill";
            PreparedStatement statement = preparedStatement(SQL);
            ResultSet myRs = statement.executeQuery();
            while (myRs.next()) {
                Skill skill = new Skill();
                skill.setId(myRs.getInt(1));
                skill.setSkillDescription(myRs.getString(2));
                skill.setStatus(setStatus(myRs.getString("skill.status")));
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
            String SQL = "update skill " +
                "set status = 'DELETE' " +
                "where skill_id = ? ";
            PreparedStatement statement = preparedStatement(SQL);
            statement.setInt(1, id.intValue());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Skill update(Skill skill) {
        try  {
            String SQL = "update skill " +
                "set skill_description = ?, status = ? " +
                "where skill_id = ? ";
            PreparedStatement statement = preparedStatement(SQL);
            statement.setString(1, skill.getSkillDescription());
            statement.setString(2, skill.getStatus().name());
            statement.setInt(3, (int)skill.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return skill;
    }

    public void assignedSkillToDeveloper(long developerId, long skillId) {
        try {
            String SQL = "INSERT INTO developer_skill(developer_id, skill_id) " +
                "values (?, ?)";
            PreparedStatement statement = preparedStatement(SQL);
            statement.setLong(1, developerId);
            statement.setLong(2, skillId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
