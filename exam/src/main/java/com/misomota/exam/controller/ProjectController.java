package com.misomota.exam.controller;

import com.misomota.exam.model.Project;
import com.misomota.exam.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/OnTheDot")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("account") != null;
    }

    @GetMapping("/projects")
    public String showAllProjects(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        List<Project> projectList = projectService.readProject();
        Map<Integer, Integer> projectDurations = new HashMap<>();
        for (Project project : projectList) {
            int totalTime = projectService.getActualTime(project.getProjectID());
            projectDurations.put(project.getProjectID(), totalTime);
        }
        model.addAttribute("projects", projectList);
        model.addAttribute("projectDurations", projectDurations);
        return "showProject";
    }

    @GetMapping("/addProject")
    public String addProject(Model model, HttpSession session) {
        model.addAttribute("project",new Project());
        return isLoggedIn(session) ? "addProject" : "redirect:/account/login";
    }

    @PostMapping("/addProject")
    public String saveProject(@ModelAttribute("project") Project project, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        projectService.createProject(project);
        return "redirect:/OnTheDot/projects";
    }

    @GetMapping("/editProject")
    public String editProject(@RequestParam("projectID") int projectID, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        Project project = projectService.findProjectByID(projectID);
        if (project != null) {
            model.addAttribute("project", project);
            return "editProject";
        } else {
            return "redirect:/OnTheDot/projects";
        }
    }

    @PostMapping("/editProject")
    public String updateProject(@ModelAttribute("project") Project project, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        projectService.updateProject(project);
        return "redirect:/OnTheDot/projects";
    }

    @PostMapping("/deleteProject")
    public String deleteProject(@RequestParam("projectID") int id,  HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        projectService.deleteProject(id);
        return "redirect:/OnTheDot/projects";
    }
}