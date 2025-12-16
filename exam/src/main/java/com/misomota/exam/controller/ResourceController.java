package com.misomota.exam.controller;

import com.misomota.exam.DRY.DatabaseOperationException;
import com.misomota.exam.DRY.NotFoundException;
import com.misomota.exam.DRY.Session;
import com.misomota.exam.model.Account;
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
    public String saveResource(@ModelAttribute("resource") Resource resource, HttpSession session, Model model) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        try {
            Resource saved = resourceService.createResource(resource);
            model.addAttribute("resource", saved);
            return "redirect:/OnTheDot/resource?taskID=" + resource.getTaskID();
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("resource", resource);
            model.addAttribute("taskID", resource.getTaskID());
            return "addResource";
        } catch (DatabaseOperationException e) {
            model.addAttribute("error", "Something went wrong. Please try again later.");
            model.addAttribute("resource", resource);
            model.addAttribute("taskID", resource.getTaskID());
            return "addResource";
        }
    }

    @GetMapping("/resource")
    public String showResource(@RequestParam("taskID") int taskID, Model model, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        Account account = (Account) session.getAttribute("account");
        model.addAttribute("account", account);

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
    public String updateResource(@ModelAttribute("resource") Resource resource, HttpSession session, Model model) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        try {
            resourceService.updateResource(resource, resource.getResourceID());
            return "redirect:/OnTheDot/resource?taskID=" + resource.getTaskID();
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("resource", resource);
            model.addAttribute("taskID", resource.getTaskID());
            return "editResource";
        } catch (DatabaseOperationException e) {
            model.addAttribute("error", "Something went wrong. Please try again later.");
            model.addAttribute("resource", resource);
            model.addAttribute("taskID", resource.getTaskID());
            return "editResource";
        } catch (NotFoundException e) {
            model.addAttribute("error", "resource not found.");
            model.addAttribute("resource", resource);
            model.addAttribute("taskID", resource.getTaskID());
            return "editResource";
        }
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