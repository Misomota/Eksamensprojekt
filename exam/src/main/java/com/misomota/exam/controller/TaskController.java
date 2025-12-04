package com.misomota.exam.controller;
import com.misomota.exam.model.Task;
import jakarta.servlet.http.HttpSession;
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

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("account") != null;
    }


    @GetMapping("/task")
    public String showTask(Model model, HttpSession session) {
        List<Task> listOfTask = taskService.showTask();
        model.addAttribute("tasks", listOfTask);
        return isLoggedIn(session) ? "showTask" : "redirect:/account/login";
    }

    @GetMapping("/addTask")
    public String addTask(Model model, HttpSession session) {
        model.addAttribute("task", new Task());
        return isLoggedIn(session) ? "addTask" : "redirect:/account/login";
    }

    @PostMapping("/addTask")
    public String saveTask(@ModelAttribute("task") Task task, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        taskService.addTask(task);
        return "redirect:/OnTheDot/task";
    }

    @PostMapping("/deleteTask")
    public String deleteTask(@RequestParam("taskID") int taskID, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        taskService.deleteTask(taskID);
        return "redirect:/OnTheDot/task";
    }

    @GetMapping("/editTask")
    public String editTask(@RequestParam("id") int taskID, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        Task task = taskService.findTaskByID(taskID);
        if (task != null) {
            model.addAttribute("task", task);
            return "editTask";
        } else {
            return "redirect:/OnTheDot/task";
        }
    }

    @PostMapping("/editTask")
    public String updateTaskName(@ModelAttribute("task") Task task, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        taskService.updateTask(task);
        return "redirect:/OnTheDot/task";
    }
}