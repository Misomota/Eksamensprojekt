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
            new Resource(rs.getInt("resourceID"), rs.getString("resourceName"));

    public Resource findResourceByID(int resourceID) {
        String sql = "SELECT resourceName, resourceID FROM resource WHERE resourceID = ?";
        return jdbcTemplate.queryForObject(sql, resourceRowMapper, resourceID);
    }

    public Resource createResource(Resource resource) {
        String sql = "INSERT INTO resource (resourceName) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, resource.getResourceName());
            return ps;
        }, keyHolder);
        int id = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;
        return new Resource(id, resource.getResourceName());
    }

    public int createResourceToTask(int taskID, int resourceID) {
        String sql = "INSERT INTO task_resource (taskID, resourceID) VALUES (?, ?)";
        return jdbcTemplate.update(sql, taskID, resourceID);
    }

    public List<Resource> readResources(int taskID) {
        String sql = "SELECT r.resourceID, r.resourceName FROM resource r " + "JOIN task_resource tr ON r.resourceID = tr.resourceID " + "WHERE tr.taskID = ?";
        return jdbcTemplate.query(sql, resourceRowMapper, taskID);
    }

    public int updateResources(Resource resource) {
        return jdbcTemplate.update("UPDATE resource SET resourceName = ? WHERE resourceID = ?", resource.getResourceName(), resource.getResourceID());
    }

    public int deleteResourceFromTask(int taskID, int resourceID) {
        String sql = "DELETE FROM task_resource WHERE taskID = ? AND resourceID = ?";
        return jdbcTemplate.update(sql, taskID, resourceID);
    }

    public boolean isResourceAssignedToTask(int taskId, int resourceId) {
        String sql = "SELECT COUNT(*) FROM task_resource WHERE taskID = ? AND resourceID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, taskId, resourceId);
        return count != null && count > 0;
    }
}
