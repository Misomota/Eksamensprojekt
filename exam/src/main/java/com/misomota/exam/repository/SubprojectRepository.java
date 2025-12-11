package com.misomota.exam.repository;

import com.misomota.exam.model.Subproject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class SubprojectRepository {
    private final JdbcTemplate jdbcTemplate;

    public SubprojectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Subproject> subprojectRowMapper = (rs, rowNum) ->
            new Subproject(
                    rs.getString("subprojectName"),
                    rs.getInt("subprojectID"),
                    0, //totalHours sættes til 0 her
                    rs.getInt("projectID")
            );

    public Subproject addSubproject(Subproject subproject, int projectID) {
        String sql = "INSERT INTO subproject (subprojectName, projectID) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, subproject.getSubprojectName());
            ps.setInt(2, projectID);
            return ps;
        }, keyHolder);

        int newId = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;
        return new Subproject(subproject.getSubprojectName(), newId, 0, projectID);
    }

    public List<Subproject> showSubproject(int projectID) {
        String sql = "SELECT subprojectID, subprojectName, projectID FROM subproject WHERE projectID = ?";
        return jdbcTemplate.query(sql, subprojectRowMapper, projectID);
    }

    public Subproject findSubprojectByID(int id) {
        String sql = "SELECT subprojectID, subprojectName, projectID FROM subproject WHERE subprojectID = ?";
        return jdbcTemplate.queryForObject(sql, subprojectRowMapper, id);
    }

    public int updateSubproject(Subproject subproject) {
        return jdbcTemplate.update("UPDATE subproject SET subprojectName = ? WHERE subprojectID = ?", subproject.getSubprojectName(), subproject.getSubprojectID());
    }

    public int deleteSubproject(int subprojectID) {
        return jdbcTemplate.update("DELETE FROM subproject WHERE subprojectID = ?", subprojectID);
    }
}