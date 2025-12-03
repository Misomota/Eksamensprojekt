package com.misomota.exam.controller;

import com.misomota.exam.model.Project;
import com.misomota.exam.model.Subproject;
import com.misomota.exam.service.ProjectService;
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

    @GetMapping("/home")
    public String showAllProjects(Model model) {
        List<Project> projectList = projectService.readProject();
        model.addAttribute("projects", projectList);
        return "showProject";
    }

    @GetMapping("/addProject")
    public String addProject(Model model) {
        model.addAttribute("project",new Project());
        return "addProject";
    }

    @PostMapping("/addProject")
    public String saveProject(@ModelAttribute("project") Project project) {
        projectService.createProject(project);
        return "redirect:/OnTheDot/home";
    }


    @GetMapping("/editProject")
    public String editProject(@RequestParam("id") int projectID, Model model) {
        Project project = projectService.findProjectByID(projectID);
        if (project != null) {
            model.addAttribute("project", project);
            return "editProject";
        } else {
            return "redirect:/OnTheDot/home";
        }
    }

    @PostMapping("/editProject")
    public String updateProject(@ModelAttribute("project") Project project) {
        projectService.updateProject(project);
        return "redirect:/OnTheDot/home";
    }

    @PostMapping("/deleteProject")
    public String deleteProject(@RequestParam("projectID") int id) {
        projectService.deleteProject(id);
        return "redirect:/OnTheDot/home";
    }
}