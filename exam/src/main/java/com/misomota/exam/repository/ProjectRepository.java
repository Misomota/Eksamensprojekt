package com.misomota.exam.repository;

import com.misomota.exam.model.Project;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ProjectRepository {

    @Value("${DEV_DATABASE_URL}")
    private String dburl;

    @Value("${DEV_DATABASE_USERNAME}")
    private String username;

    @Value("${DEV_DATABASE_PASSWORD}")
    private String password;

    private final JdbcTemplate jdbcTemplate;


    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Project> projectRowmapper = (rs, rowNum) ->
            new Project(
                    rs.getString("ProjectName"),
                    rs.getInt("ProjectID")
            );


    public List<Project> readProject() {
        String sql = "SELECT projectName, projectID FROM project";
        return jdbcTemplate.query(sql, projectRowmapper);
    }

    public Project readProjectByID(int projectID) {
        String sql = "SELECT projectName, projectID FROM project WHERE projectID = ?";
        return jdbcTemplate.queryForObject(sql, projectRowmapper, projectID);
    }

    public Project createProject(Project project) {
        String sql = "INSERT INTO project (projectName) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, project.getProjectName());
            return ps;
        }, keyHolder);

        int newID = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;
        return new Project(project.getProjectName(), newID);
    }

    public List<Project> readProject() {
        String sql = "SELECT projectName, projectID FROM project";
        return jdbcTemplate.query(sql, projectRowmapper);
    }

    public Project readProjectByID(int projectID) {
        String sql = "SELECT projectName, projectID FROM project WHERE projectID = ?";
        return jdbcTemplate.queryForObject(sql, projectRowmapper, projectID);
    }

    public void updateProject(Project project) {
        String sql = "UPDATE project SET projectName = ? WHERE projectID = ?";
        jdbcTemplate.update(sql, project.getProjectName(), project.getProjectID());
    }

    public void deleteProject(int projectID) {
        String sql = "DELETE FROM project WHERE projectID = ?";
        jdbcTemplate.update(sql, projectID);
    }
}