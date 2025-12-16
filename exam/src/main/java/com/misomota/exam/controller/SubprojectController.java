package com.misomota.exam.controller;

import com.misomota.exam.DRY.DatabaseOperationException;
import com.misomota.exam.DRY.NotFoundException;
import com.misomota.exam.DRY.Session;
import com.misomota.exam.model.Account;
import com.misomota.exam.model.Subproject;
import com.misomota.exam.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.misomota.exam.service.SubprojectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/OnTheDot")
public class SubprojectController {
    private final SubprojectService subprojectService;
    private final TaskService taskService;

    public SubprojectController(SubprojectService subprojectService, TaskService taskService) {
        this.subprojectService = subprojectService;
        this.taskService = taskService;
    }

    @GetMapping("/subproject")
    public String showSubproject(@RequestParam("projectID") int projectID, Model model, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        Account account = (Account) session.getAttribute("account");
        model.addAttribute("account", account);

        List<Subproject> listOfSubproject = subprojectService.readSubproject(projectID);

        for (Subproject sp : listOfSubproject) {
            int hours = taskService.sumHoursForSubproject(sp.getSubprojectID());
            sp.setTotalHours(hours);
        }

        model.addAttribute("subprojects", listOfSubproject);
        model.addAttribute("projectID", projectID);

        return Session.isLoggedIn(session) ? "showSubproject" : "redirect:/account/login";
    }

    @GetMapping("/addSubproject")
    public String addSubproject(@RequestParam("projectID") int projectID, Model model, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        model.addAttribute("subproject", new Subproject());
        model.addAttribute("projectID", projectID);
        return Session.isLoggedIn(session) ? "addSubproject" : "redirect:/account/login";
    }

    @PostMapping("/addSubproject")
    public String saveSubproject(@RequestParam("projectID") int projectID, @ModelAttribute("subproject") Subproject subproject, HttpSession session, Model model) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        try {
            Subproject saved = subprojectService.createSubproject(subproject, projectID);
            model.addAttribute("subproject", saved);
            return "redirect:/OnTheDot/subproject?projectID=" + projectID;
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("subproject", subproject);
            model.addAttribute("projectID", projectID);
            return "addSubproject";
        } catch (DatabaseOperationException e) {
            model.addAttribute("error", "Something went wrong. Please try again later.");
            model.addAttribute("subproject", subproject);
            model.addAttribute("projectID", projectID);
            return "addSubproject";
        }
    }

    @PostMapping("/deleteSubproject")
    public String deleteSubproject(@RequestParam("subprojectID") int subprojectID, @RequestParam("projectID") int projectID, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        subprojectService.deleteSubproject(subprojectID);
        return "redirect:/OnTheDot/subproject?projectID=" + projectID;
    }

    @GetMapping("/editSubproject")
    public String editSubproject(@RequestParam("subprojectID") int subprojectID, @RequestParam("projectID") int projectID, Model model, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        Subproject subproject = subprojectService.findSubprojectByID(subprojectID);
        if (subproject != null) {
            model.addAttribute("subproject", subproject);
            model.addAttribute("projectID", projectID);
            return "editSubproject";
        } else {
            return "redirect:/OnTheDot/subproject?projectID=" + projectID;

        }
    }

    @PostMapping("/editSubproject")
    public String updateSubproject(@RequestParam("projectID") int projectID, @ModelAttribute("subproject") Subproject subproject, HttpSession session, Model model) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        try {
            subprojectService.updateSubproject(subproject, subproject.getSubprojectID());
            return "redirect:/OnTheDot/subproject?projectID=" + projectID;
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("subproject", subproject);
            model.addAttribute("projectID", projectID);
            return "editSubproject";
        } catch (DatabaseOperationException e) {
            model.addAttribute("error", "Something went wrong. Please try again later.");
            model.addAttribute("subproject", subproject);
            model.addAttribute("projectID", projectID);
            return "editSubproject";
        } catch (NotFoundException e) {
            model.addAttribute("error", "subproject not found.");
            model.addAttribute("subproject", subproject);
            model.addAttribute("projectID", projectID);
            return "editSubproject";
        }
    }
}