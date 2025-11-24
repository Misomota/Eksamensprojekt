package com.misomota.exam.controller;

import com.misomota.exam.model.Project;
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

    @GetMapping("/Home")
    public String showProject(Model model) {
        List<Project> projectList = projectService.showProject();
        model.addAttribute("project", projectList);
        return "project";
    }

    @GetMapping("/addProject")
    public String addProject(Model model) {
        model.addAttribute("project",new Project());
        return "addProject";
    }

    @PostMapping("/addProject")
    public String saveProject(@ModelAttribute("project") Project project) {
        projectService.addProject(project);
        return "redirect:/OnTheDot/Projects";
    }


    @GetMapping("/editProject")
    public String editProject(@RequestParam("id") int id, Model model) {
        model.addAttribute("project", id);
        return "editProject";
    }

    @PostMapping("/editProject")
    public String updateProject(@ModelAttribute("project") Project project) {
        projectService.updateProject(project);
        return "redirect:/Projects";
    }

    @PostMapping("/deleteProject")
    public String deleteProject(@RequestParam("projectID") int id) {
        projectService.deleteProject(id);
        return "redirect:/OnTheDot/Projects";
    }

}
