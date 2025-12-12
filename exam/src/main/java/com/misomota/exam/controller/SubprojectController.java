package com.misomota.exam.controller;

import com.misomota.exam.DRY.Session;
import com.misomota.exam.model.Subproject;
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

    public SubprojectController(SubprojectService subprojectService) {
        this.subprojectService = subprojectService;
    }

    @GetMapping("/subproject")
    public String showSubproject(@RequestParam("projectID") int projectID, Model model, HttpSession session) {
        List<Subproject> listOfSubproject = subprojectService.readSubproject(projectID);
        model.addAttribute("subprojects", listOfSubproject);
        model.addAttribute("projectID", projectID);
        System.out.println("All subprojects: " + subprojectService.readSubproject(projectID));
        return Session.isLoggedIn(session) ? "showSubproject" : "redirect:/account/login";
    }

    @GetMapping("/addSubproject")
    public String addSubproject(@RequestParam("projectID") int projectID, Model model, HttpSession session) {
        model.addAttribute("subproject", new Subproject());
        model.addAttribute("projectID", projectID);
        return Session.isLoggedIn(session) ? "addSubproject" : "redirect:/account/login";
    }

    @PostMapping("/addSubproject")
    public String saveSubproject(@RequestParam("projectID") int projectID,@ModelAttribute("subproject") Subproject subproject, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        subprojectService.createSubproject(subproject, projectID);
        return "redirect:/OnTheDot/subproject?projectID=" + projectID;
    }

    @PostMapping("/deleteSubproject")
    public String deleteSubproject(@RequestParam("subprojectID") int subprojectID,@RequestParam("projectID") int projectID, HttpSession session) {
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
    public String updateSubproject(@RequestParam("projectID") int projectID, @ModelAttribute("subproject") Subproject subproject, HttpSession session) {
        if (!Session.isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        subprojectService.updateSubproject(subproject, subproject.getSubprojectID());
        return "redirect:/OnTheDot/subproject?projectID=" + projectID;
    }
}