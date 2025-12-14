package com.misomota.exam.service;

import com.misomota.exam.DRY.DatabaseOperationException;
import com.misomota.exam.DRY.NotFoundException;
import com.misomota.exam.model.Task;
import com.misomota.exam.repository.TaskRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task, int subprojectID) {
        return taskRepository.createTask(task, subprojectID);
    }

    public List<Task> readTask(int subprojectID) {
        List<Task> tasks = taskRepository.readTask(subprojectID);
        for (Task task : tasks) {
            int duration = calculateDuration(task);
            task.setDuration(duration);
            int hoursEstimated = timeEstimate(task);
            task.setTimeEstimate(hoursEstimated);
        }
        return tasks;
    }

    public Task findTaskByID(int id) {
        Task task = taskRepository.findTaskByID(id);

        if (task == null) {
            throw new NotFoundException("No tasks found");
        }
        return task;
    }

    public Task updateTask(Task task, int id) {
        try {
            Task existing = findTaskByID(id);

            existing.setTaskName(task.getTaskName());
            existing.setStartDate(task.getStartDate());
            existing.setDeadline(task.getDeadline());
            existing.setPersonAssigned(task.getPersonAssigned());
            existing.setActualTimeUsed(task.getActualTimeUsed());

            int rows = taskRepository.updateTask(existing);
            if (rows == 0) throw new NotFoundException(id);
            return existing;
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Failed to update project", e);
        }
    }

    public void deleteTask(int id) {
        try {
            int rows = taskRepository.deleteTask(id);
            if (rows == 0) throw new NotFoundException(id);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Failed to delete project", e);
        }

    }

    public int calculateDuration(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }

        LocalDate start = task.getStartDate();
        LocalDate end = task.getDeadline();

        if (start == null || end == null) {
            throw new IllegalArgumentException("Task start date and deadline cannot be null");
        }

        if (end.isBefore(start)) {
            throw new IllegalArgumentException("Deadline cannot be before start date");
        }
        return (int) ChronoUnit.DAYS.between(start, end) + 1;
    }

    public int timeEstimate(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }

        int duration = task.getDuration();

        if (duration <= 0) {
            throw new IllegalStateException("Duration cannot be less than zero");
        }

        return duration * 8;
    }

    public int sumHoursForSubproject(int subprojectID) {
        List<Task> tasks = taskRepository.readTask(subprojectID);
        return tasks.stream()
                .mapToInt(Task::getActualTimeUsed)
                .sum();
    }
}