package com.misomota.exam.service;

import com.misomota.exam.model.Subproject;
import com.misomota.exam.model.Task;
import com.misomota.exam.repository.SubprojectRepository;
import com.misomota.exam.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final SubprojectRepository subprojectRepository;

    public TaskService(TaskRepository taskRepository, SubprojectRepository subprojectRepository) {
        this.taskRepository = taskRepository;
        this.subprojectRepository = subprojectRepository;
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

    public int getActualTimeForProject(int projectID) {
        List<Subproject> subprojects = subprojectRepository.findSubprojectsByProjectId(projectID);
        int totalActualTime = 0;
        for (Subproject s : subprojects) {
            List<Task> tasks = taskRepository.showTask(s.getSubprojectID());
            for (Task task : tasks) {
                totalActualTime += task.getActualTimeUsed();
            }
        }
        return totalActualTime;
    }
}