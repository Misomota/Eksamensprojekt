package com.misomota.exam.repository;

import com.misomota.exam.model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
                    rs.getTimestamp("deadline").toLocalDateTime(),
                    rs.getInt("timeEstimate")
            );

    public Task addTask(Task task) {
        String sql = "INSERT INTO task (taskName, startDate, deadline, timeEstimate) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getTaskName());
            ps.setDate(2, java.sql.Date.valueOf(task.getStartDate()));
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(task.getDeadline()));
            ps.setInt(4, task.getTimeEstimate());
            return ps;
        }, keyHolder);

        int newID = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;
        return new Task(newID, task.getTaskName(), task.getStartDate(), task.getDeadline(), task.getTimeEstimate());
    }

    public List<Task> showTask() {
        String sql = "SELECT taskID, taskName, startDate, deadline, timeEstimate FROM task";
        return jdbcTemplate.query(sql, taskRowMapper);
    }

    public Task findTaskByID(int id) {
        String sql = "SELECT taskID, taskName, startDate, deadline, timeEstimate FROM task WHERE taskID = ?";
        return jdbcTemplate.queryForObject(sql, taskRowMapper, id);
    }

    public void updateTaskName(int taskID, String newName) {
        String sql = "UPDATE task SET taskName = ? WHERE taskID = ?";
        jdbcTemplate.update(sql, newName, taskID);
    }

    public void updateTaskDates(int taskID, LocalDate startDate, LocalDateTime deadline, int timeEstimate) {
        String sql = "UPDATE task SET startDate = ?, deadline = ?, timeEstimate = ? WHERE taskID = ?";
        jdbcTemplate.update(sql, java.sql.Date.valueOf(startDate), java.sql.Timestamp.valueOf(deadline), timeEstimate, taskID
        );
    }

    public void deleteTask(int taskID) {
        String sql = "DELETE FROM task WHERE taskID = ?";
        jdbcTemplate.update(sql, taskID);
    }
}