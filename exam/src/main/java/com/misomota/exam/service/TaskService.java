package com.misomota.exam.service;

import com.misomota.exam.model.Task;
import com.misomota.exam.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
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
            calculateDuration(task);
        }
        return tasks;
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

    public double calculateRequiredDays(Task task) {
        int totalHours = task.getTimeEstimate();
        int person = task.getPersonAssigned();
        if (person<=0) {
            throw new IllegalArgumentException("The amount of people assigned must be greater than 0");
        }
        return (double) totalHours / (8 * person);
    }

    public boolean canFinishBeforeDeadline(Task task) {
        int hoursPerDay = task.getPersonAssigned() * 8;
        double requiredDays = (double) task.getTimeEstimate() / hoursPerDay;

        LocalDate current = task.getStartDate();
        int availableDays = 0;

        while (!current.isAfter(task.getDeadline())) {
            availableDays++;
            current = current.plusDays(1);
        }
        return requiredDays <= availableDays;
    }
      
    public long calculateDuration(Task task) {
        LocalDate start = task.getStartDate();
        LocalDate end = task.getDeadline();
        return Duration.between(start, end).toDays();
    }
}