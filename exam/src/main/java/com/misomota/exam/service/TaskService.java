package com.misomota.exam.service;

import com.misomota.exam.model.Task;
import com.misomota.exam.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> showTask(Task task) {
        calculateDuration(task);
        return taskRepository.showTask();
    }

    public Task findTaskByID(int id) {
        return taskRepository.findTaskByID(id);
    }

    public Task addTask(Task task) {
        return taskRepository.addTask(task);
    }

    public void deleteTask(int id) {
        taskRepository.deleteTask(id);
    }

    public void updateTaskName(int task, String newName) {
        taskRepository.updateTaskName(task, newName);
    }

    public void updateTaskDate(int taskID, LocalDate startDate, LocalDateTime deadline, int timeEstimate, String resource) {
        taskRepository.updateTaskDates(taskID, startDate, deadline, timeEstimate, resource);
    }

    public long calculateDuration(Task task) {
        LocalDateTime start = task.getStartDate().atStartOfDay();
        LocalDateTime end = task.getDeadline();
        Duration duration = Duration.between(start, end);
        return duration.toDays();
    }
}