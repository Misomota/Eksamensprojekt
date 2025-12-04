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
    public List<Task> showTask(int subprojectID) {
        return taskRepository.showTask(subprojectID);
    }

    public Task findTaskByID(int id) {
        return taskRepository.findTaskByID(id);
    }

    public Task addTask(Task task, int subprojectID) {
        return taskRepository.addTask(task, subprojectID);
    }

    public void deleteTask(int taksID) {
        taskRepository.deleteTask(taksID);
    }

    public void updateTask(Task task) {
        taskRepository.updateTask(task);
    }

    public long calculateDuration(Task task) {
        LocalDateTime start = task.getStartDate().atStartOfDay();
        LocalDate end = task.getDeadline();
        Duration duration = Duration.between(start, end);
        return duration.toDays();
    }
}