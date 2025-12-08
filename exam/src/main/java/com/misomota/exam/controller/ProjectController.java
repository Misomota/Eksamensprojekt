package com.misomota.exam.controller;

import com.misomota.exam.DRY.Session;
import com.misomota.exam.model.Project;
import com.misomota.exam.model.Subproject;
import com.misomota.exam.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/OnTheDot")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public String showAllProjects(Model model, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        List<Project> projectList = projectService.readProject();
        model.addAttribute("projects", projectList);
        return "showProject";
    }

    @GetMapping("/addProject")
    public String addProject(Model model, HttpSession session) {
        model.addAttribute("project",new Project());
        return Session.isLoggedIn(session) ? "addProject" : "redirect:/account/login";
    }

    @PostMapping("/addProject")
    public String saveProject(@ModelAttribute("project") Project project, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        projectService.createProject(project);
        return "redirect:/OnTheDot/projects";
    }

    @GetMapping("/editProject")
    public String editProject(@RequestParam("projectID") int projectID, Model model, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
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
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        projectService.updateProject(project, project.getProjectID());
        return "redirect:/OnTheDot/projects";
    }

    @PostMapping("/deleteProject")
    public String deleteProject(@RequestParam("projectID") int id,  HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        projectService.deleteProject(id);
        return "redirect:/OnTheDot/projects";
    }
}