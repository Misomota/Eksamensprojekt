package com.misomota.exam.controller;

import com.misomota.exam.DRY.Session;
import com.misomota.exam.model.Resource;
import com.misomota.exam.service.ResourceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/OnTheDot")
public class ResourceController {
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/addResource")
    public String createResource(@RequestParam("taskID") int taskID, Model model, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        Resource resource = new Resource();
        resource.setTaskID(taskID);
        model.addAttribute("resource", resource);
        model.addAttribute("taskID", taskID);
        return "addResource";
    }

    @PostMapping("/addResource")
    public String saveResource(@ModelAttribute("resource") Resource resource, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        resourceService.createResource(resource);
        return "redirect:/OnTheDot/resource?taskID=" + resource.getTaskID();
    }

    @GetMapping("/resource")
    public String showResource(@RequestParam("taskID") int taskID, Model model, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        List<Resource> resources = resourceService.readResources(taskID);
        model.addAttribute("taskID", taskID);
        model.addAttribute("resources", resources);
        return "showResources";
    }

    @GetMapping("/updateResource")
    public String editResource(@RequestParam("resourceID") int resourceID, @RequestParam("taskID") int taskID, Model model, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        Resource resource = resourceService.findResourceByID(resourceID);
        model.addAttribute("resource", resource);
        model.addAttribute("taskID", taskID);
        return "editResource";
    }

    @PostMapping("/updateResource")
    public String updateResource(@ModelAttribute("resource") Resource resource, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        resourceService.updateResource(resource, resource.getResourceID());
        return "redirect:/OnTheDot/resource?taskID=" + resource.getTaskID();
    }


    @PostMapping("/deleteResource")
    public String deleteResource(@RequestParam("taskID") int taskID, @RequestParam("resourceID") int resourceID, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        resourceService.deleteResource(resourceID);
        return "redirect:/OnTheDot/resource?taskID=" + taskID;
    }
}