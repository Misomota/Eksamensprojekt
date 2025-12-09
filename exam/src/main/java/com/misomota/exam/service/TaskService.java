package com.misomota.exam.service;

import com.misomota.exam.model.Task;
import com.misomota.exam.repository.TaskRepository;
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
        return taskRepository.findTaskByID(id);
    }

    public Task createTask(Task task, int subprojectID) {
        return taskRepository.createTask(task, subprojectID);
    }

    public void deleteTask(int taskID) {
        taskRepository.deleteTask(taskID);
    }

    public void updateTask(Task task) {
        taskRepository.updateTask(task);
    }
      
    public int calculateDuration(Task task) {
        LocalDate start = task.getStartDate();
        LocalDate end = task.getDeadline();
        return (int) ChronoUnit.DAYS.between(start, end) + 1;
    }

    public int timeEstimate(Task task) {
        int hoursPerDay = task.getDuration() * 8;
        return hoursPerDay;
    }
}