package com.misomota.exam.controller;

import com.misomota.exam.DRY.DatabaseOperationException;
import com.misomota.exam.DRY.NotFoundException;
import com.misomota.exam.DRY.Session;
import com.misomota.exam.model.Subproject;
import com.misomota.exam.model.Task;
import com.misomota.exam.service.SubprojectService;
import com.misomota.exam.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/OnTheDot")
public class TaskController {
    private final TaskService taskService;
    private final SubprojectService subprojectService;

    public TaskController(TaskService taskService, SubprojectService subprojectService) {
        this.taskService = taskService;
        this.subprojectService = subprojectService;
    }

    @GetMapping("/task")
    public String readTask(@RequestParam("subprojectID") int subprojectID, Model model, HttpSession session) {
        List<Task> listOfTask = taskService.readTask(subprojectID);
        model.addAttribute("tasks", listOfTask);
        model.addAttribute("subprojectID", subprojectID);

        Subproject subproject = subprojectService.findSubprojectByID(subprojectID);
        int projectID = subproject.getProjectID();
        model.addAttribute("projectID", projectID);

        return Session.isLoggedIn(session) ? "showTask" : "redirect:/account/login";
    }

    @GetMapping("/addTask")
    public String createTask(@RequestParam("subprojectID") int subprojectID, Model model, HttpSession session) {
        model.addAttribute("task", new Task());
        model.addAttribute("subprojectID", subprojectID);
        return Session.isLoggedIn(session) ? "addTask" : "redirect:/account/login";
    }

    @PostMapping("/addTask")
    public String saveTask(@RequestParam("subprojectID") int subprojectID, @ModelAttribute("task") Task task, HttpSession session, Model model) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        try {
            Task saved = taskService.createTask(task, subprojectID);
            model.addAttribute("task", saved);
            return "redirect:/OnTheDot/task?subprojectID=" + subprojectID;
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("task", task);
            model.addAttribute("subprojectID", subprojectID);
            return "addTask";
        } catch (DatabaseOperationException e) {
            model.addAttribute("error", "Database error: " + e.getMessage());
            model.addAttribute("task", task);
            model.addAttribute("subprojectID", subprojectID);
            return "addTask";
        }
    }

    @PostMapping("/deleteTask")
    public String deleteTask(@RequestParam("taskID") int taskID, @RequestParam("subprojectID") int subprojectID, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        taskService.deleteTask(taskID);
        return "redirect:/OnTheDot/task?subprojectID=" + subprojectID;

    }

    @GetMapping("/editTask")
    public String editTask(@RequestParam("taskID") int taskID, @RequestParam("subprojectID") int subprojectID, Model model, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
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
    public String updateTaskName(@RequestParam("subprojectID") int subprojectID, @ModelAttribute("task") Task task, HttpSession session, Model model) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        try {
            taskService.updateTask(task, task.getTaskID());
            return "redirect:/OnTheDot/task?subprojectID=" + subprojectID;
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("task", task);
            model.addAttribute("subprojectID", subprojectID);
            return "editTask";
        } catch ( DatabaseOperationException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("task", task);
            model.addAttribute("subprojectID", subprojectID);
            return "editTask";
        }
        catch (NotFoundException e) {
            model.addAttribute("error", "task not found.");
            model.addAttribute("task", task);
            model.addAttribute("subprojectID", subprojectID);
            return "editTask";
        }
    }

    @GetMapping("/ganttDiagram")
    public String readGantt(@RequestParam("subprojectID") int subprojectID, Model model, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        List<Task> tasks = taskService.readTask(subprojectID);

        LocalDate projectStart = tasks.stream()
                .map(Task::getStartDate)
                .min(LocalDate::compareTo)
                .orElse(LocalDate.now());

        tasks.forEach(task -> {
            long offsetDays = ChronoUnit.DAYS.between(projectStart, task.getStartDate());
            task.setTimeEstimate((int) offsetDays);
        });

        int totalDays = tasks.stream()
                .mapToInt(t -> t.getTimeEstimate() + t.getDuration())
                .max()
                .orElse(10);

        List<String> days = new ArrayList<>();
        for (int i = 0; i < totalDays; i++) {
            days.add(projectStart.plusDays(i).format(DateTimeFormatter.ofPattern("MM/dd")));
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("projectStart", projectStart);
        model.addAttribute("subprojectID", subprojectID);
        model.addAttribute("days", days);
        return "ganttDiagram";
    }
}