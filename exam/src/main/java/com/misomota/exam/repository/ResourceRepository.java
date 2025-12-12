package com.misomota.exam.repository;

import com.misomota.exam.model.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ResourceRepository {

    private final JdbcTemplate jdbcTemplate;

    public ResourceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Resource> resourceRowMapper = (rs, rowNum) ->
            new Resource(
                    rs.getInt("resourceID"),
                    rs.getString("resourceName"),
                    rs.getInt("taskID")
            );


    public Resource findResourceByID(int resourceID) {
        String sql = "SELECT resourceID, resourceName, taskID FROM resource WHERE resourceID = ?";
        return jdbcTemplate.queryForObject(sql, resourceRowMapper, resourceID);
    }

    public Resource createResource(Resource resource) {
        String sql = "INSERT INTO resource (resourceName, taskID) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps =
                    connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, resource.getResourceName());
            ps.setInt(2, resource.getTaskID());
            return ps;
        }, keyHolder);

        int id = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;

        return new Resource(id, resource.getResourceName(), resource.getTaskID());
    }

    public List<Resource> readResources(int taskID) {
        String sql = "SELECT resourceID, resourceName, taskID FROM resource WHERE taskID = ?";
        return jdbcTemplate.query(sql, resourceRowMapper, taskID);
    }

    public int updateResources(Resource resource) {
        String sql = "UPDATE resource SET resourceName = ?, taskID = ? WHERE resourceID = ?";
        return jdbcTemplate.update(sql,
                resource.getResourceName(),
                resource.getTaskID(),
                resource.getResourceID()
        );
    }

    public int deleteResource(int resourceID) {
        String sql = "DELETE FROM resource WHERE resourceID = ?";
        return jdbcTemplate.update(sql, resourceID);
    }
}
