package com.misomota.exam.controller;
import com.misomota.exam.model.Task;
import org.springframework.ui.Model;
import com.misomota.exam.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/OnTheDot")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/task")
    public String showTask(Model model) {
        List<Task> listOfTask = taskService.showTask();
        model.addAttribute("tasks", listOfTask);
        return "showTask";
    }

    @GetMapping("/addTask")
    public String addTask(Model model) {
        model.addAttribute("task", new Task());
        return "addTask";
    }

    @PostMapping("/addTask")
    public String saveTask(@ModelAttribute("task") Task task) {
        taskService.addTask(task);
        return "redirect:/OnTheDot/task";
    }

    @PostMapping("/deleteTask")
    public String deleteTask(@RequestParam("taskID") int taskID) {
        taskService.deleteTask(taskID);
        return "redirect:/OnTheDot/task";
    }

    @GetMapping("/editTask")
    public String editTask(@RequestParam("id") int taskID, Model model) {
        Task task = taskService.findTaskByID(taskID);
        if (task != null) {
            model.addAttribute("task", task);
            return "editTask";
        } else {
            return "redirect:/OnTheDot/task";
        }
    }

    @PostMapping("/editTask")
    public String updateTaskName(@ModelAttribute("task") Task task) {
        taskService.updateTask(task);
        return "redirect:/OnTheDot/task";
    }
}