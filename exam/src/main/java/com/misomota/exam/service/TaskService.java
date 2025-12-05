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

    public List<Task> showTask(int subprojectID) {
        List<Task> tasks = taskRepository.showTask(subprojectID);
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

    public Task addTask(Task task, int subprojectID) {
        return taskRepository.addTask(task, subprojectID);
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
        return (int) ChronoUnit.DAYS.between(start, end);
    }

    public int timeEstimate(Task task) {
        int hoursPerDay = task.getDuration() * 8;
        return hoursPerDay;
    }
}