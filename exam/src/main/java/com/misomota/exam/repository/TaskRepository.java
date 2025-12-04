package com.misomota.exam.repository;

import com.misomota.exam.model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TaskRepository {
    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Task> taskRowMapper = (rs, rowNum) ->
            new Task(
                    rs.getInt("taskID"),
                    rs.getString("taskName"),
                    rs.getDate("startDate").toLocalDate(),
                    rs.getDate("deadline").toLocalDate(),
                    rs.getInt("timeEstimate"),
                    rs.getString("resource"),
                    rs.getInt("duration")
            );

    public Task addTask(Task task, int subprojectID) {
        String sql = "INSERT INTO task (taskName, startDate, deadline, timeEstimate, `resource`, duration, subprojectID) VALUES (?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getTaskName());
            ps.setDate(2, Date.valueOf(task.getStartDate()));
            ps.setDate(3, Date.valueOf(task.getDeadline()));
            ps.setInt(4, task.getTimeEstimate());
            ps.setString(5,task.getResource());
            ps.setInt(6, task.getDuration());
            ps.setInt(7, subprojectID);
            return ps;
        }, keyHolder);

        int newID = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;
        return new Task(newID, task.getTaskName(), task.getStartDate(), task.getDeadline(),task.getTimeEstimate(), task.getResource(), task.getDuration());
    }

    public List<Task> showTask(int subprojectID) {
        String sql = "SELECT taskID, taskName, startDate, deadline, timeEstimate, `resource`, duration FROM task WHERE subprojectID = ?";
        return jdbcTemplate.query(sql, taskRowMapper, subprojectID);
    }

    public Task findTaskByID(int id) {
        String sql = "SELECT taskID, taskName, startDate, deadline, timeEstimate, resource, duration FROM task WHERE taskID = ?";
        return jdbcTemplate.queryForObject(sql, taskRowMapper, id);
    }

    public void updateTask(Task task) {
        String sql = "UPDATE task SET taskName = ?, startDate = ?, deadline = ?, timeEstimate = ?, `resource` = ?, duration = ? WHERE taskID = ?";
        jdbcTemplate.update(sql, task.getTaskName(), java.sql.Date.valueOf(task.getStartDate()), java.sql.Date.valueOf(task.getDeadline()), task.getTimeEstimate(), task.getResource(), task.getTaskID(), task.getDuration());
    }


    public void deleteTask(int taskID) {
        String sql = "DELETE FROM task WHERE taskID = ?";
        jdbcTemplate.update(sql, taskID);
    }
}