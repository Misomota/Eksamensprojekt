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
    public String showTask(@RequestParam("subprojectID") int subprojectID, Model model, HttpSession session) {
        List<Task> listOfTask = taskService.showTask(subprojectID);
        model.addAttribute("tasks", listOfTask);
        model.addAttribute("subprojectID", subprojectID);
        return isLoggedIn(session) ? "showTask" : "redirect:/account/login";
    }

    @GetMapping("/addTask")
    public String addTask(@RequestParam("subprojectID") int subprojectID, Model model, HttpSession session) {
        model.addAttribute("task", new Task());
        model.addAttribute("subprojectID", subprojectID);
        return isLoggedIn(session) ? "addTask" : "redirect:/account/login";
    }

    @PostMapping("/addTask")
    public String saveTask(@RequestParam("subprojectID" )int subprojectID,@ModelAttribute("task") Task task, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        taskService.addTask(task, subprojectID);
        return "redirect:/OnTheDot/task?subprojectID=" + subprojectID;

    }

    @PostMapping("/deleteTask")
    public String deleteTask(@RequestParam("taskID") int taskID,@RequestParam("subprojectID") int subprojectID, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        taskService.deleteTask(taskID);
        return "redirect:/OnTheDot/task?subprojectID=" + subprojectID;

    }

    @GetMapping("/editTask")
    public String editTask(@RequestParam("taskID") int taskID, @RequestParam("subprojectID") int subprojectID, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        Task task = taskService.findTaskByID(taskID);
        if (task != null) {
            model.addAttribute("task", task);
            model.addAttribute("subprojectID", subprojectID);
            return "editTask";
        } else {
            return "redirect:/OnTheDot/task?subprojectID=" + subprojectID;

        }
    }

    @PostMapping("/editTask")
    public String updateTaskName(@RequestParam("subprojectID") int subprojectID, @ModelAttribute("task") Task task, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        taskService.updateTask(task);
        return "redirect:/OnTheDot/task?subprojectID=" + subprojectID;
    }
}