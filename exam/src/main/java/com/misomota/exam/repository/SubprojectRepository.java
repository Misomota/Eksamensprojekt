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
                    rs.getInt("subprojectID")
            );

    public Subproject addSubproject(Subproject subproject) {
        String sql = "INSERT INTO subproject (subprojectName) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, subproject.getSubprojectName());
            return ps;
        }, keyHolder);

        int newId = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;
        return new Subproject(subproject.getSubprojectName(), newId);
    }

    public List<Subproject> showSubproject() {
        String sql = "SELECT subprojectID, subprojectName FROM subproject";
        return jdbcTemplate.query(sql, subprojectRowMapper);
    }

    public Subproject findSubprojectByID(int id) {
        String sql = "SELECT subprojectID, subprojectName FROM subproject WHERE subprojectID = ?";
        return jdbcTemplate.queryForObject(sql, subprojectRowMapper, id);
    }

    public void updateSubproject(Subproject subproject) {
        String sql = "UPDATE subproject SET subprojectName = ? WHERE subprojectID = ?";
        jdbcTemplate.update(sql, subproject.getSubprojectName(), subproject.getSubprojectID());
    }

    public void deleteSubproject(int subprojectID) {
        String sql = "DELETE FROM subproject WHERE subprojectID = ?";
        jdbcTemplate.update(sql, subprojectID);
    }
}