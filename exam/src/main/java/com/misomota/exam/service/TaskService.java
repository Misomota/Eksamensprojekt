package com.misomota.exam.service;

import com.misomota.exam.model.Task;
import com.misomota.exam.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

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
}