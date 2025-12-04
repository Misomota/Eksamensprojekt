package com.misomota.exam.controller;

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

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("account") != null;
    }

    @GetMapping("/subproject")
    public String showSubproject(Model model, HttpSession session) {
        List<Subproject> listOfSubproject = subprojectService.showSubproject();
        model.addAttribute("subproject", listOfSubproject);
        return isLoggedIn(session) ? "showSubproject" : "redirect:/account/login";
    }

    @GetMapping("/addSubproject")
    public String addSubproject(Model model, HttpSession session) {
        model.addAttribute("subproject", new Subproject());
        return isLoggedIn(session) ? "addSubproject" : "redirect:/account/login";
    }

    @PostMapping("/addSubproject")
    public String saveSubproject(@ModelAttribute("subproject") Subproject subproject, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        subprojectService.addSubproject(subproject);
        return "redirect:/OnTheDot/subproject";
    }

    @PostMapping("/deleteSubproject")
    public String deleteSubproject(@RequestParam("id") int subprojectID, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        subprojectService.deleteSubproject(subprojectID);
        return "redirect:/OnTheDot/subproject";
    }

    @GetMapping("/editSubproject")
    public String editSubproject(@RequestParam("id") int subprojectID, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        Subproject subproject = subprojectService.findSubprojectByID(subprojectID);
        if (subproject != null) {
            model.addAttribute("subproject", subproject);
            return "editSubproject";
        } else {
            return "redirect:/OnTheDot/subproject";
        }
    }

    @PostMapping("/editSubproject")
    public String updateSubproject(@ModelAttribute("subproject") Subproject subproject, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/account/login";
        }
        subprojectService.updateSubproject(subproject);
        return "redirect:/OnTheDot/subproject";
    }
}