package com.misomota.exam.Controller;

import com.misomota.exam.model.Subproject;
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
    public String showSubproject(Model model) {
        List<Subproject> listOfSubproject = subprojectService.showSubproject();
        model.addAttribute("subproject", listOfSubproject);
        return "showSubproject";
    }

    @GetMapping("/addSubproject")
    public String addSubproject(Model model) {
        model.addAttribute("subproject", new Subproject());
        return "addSubproject";
    }

    @PostMapping("/addSubproject")
    public String saveSubproject(@ModelAttribute("subproject") Subproject subproject) {
        subprojectService.addSubproject(subproject);
        return "redirect:/OnTheDot/subproject";
    }

    @PostMapping("/deleteSubproject")
    public String deleteSubproject(@RequestParam("id") int subprojectID) {
        subprojectService.deleteSubproject(subprojectID);
        return "redirect:/OnTheDot/subproject";
    }

    @GetMapping("/editSubproject")
    public String editSubproject(@RequestParam("id") int subprojectID, Model model) {
        Subproject subproject = subprojectService.findSubprojectByID(subprojectID);
        if (subproject != null) {
            model.addAttribute("subproject", subproject);
            return "editSubproject";
        } else {
            return "redirect:/OnTheDot/subproject";
        }
    }

    @PostMapping("/editSubproject")
    public String updateSubproject(@ModelAttribute("subproject") Subproject subproject) {
        subprojectService.updateSubproject(subproject);
        return "redirect:/OnTheDot/subproject";
    }
}