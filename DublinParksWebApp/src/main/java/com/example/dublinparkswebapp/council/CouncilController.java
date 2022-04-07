package com.example.dublinparkswebapp.council;

import com.example.dublinparkswebapp.park.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
public class CouncilController {
    @Autowired
    private CouncilService councilService;

    @Autowired
    private ParkService parkService;

    @GetMapping("/councils")
    public String showCouncilList(Model model) {
        List<Council> listOfCouncils = councilService.listAll();
        model.addAttribute("listOfCouncils", listOfCouncils);
        return "councils";

    }


    @GetMapping("/councils/view/{id}")
    public String showCouncilById(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Council council = councilService.get(id);
            model.addAttribute("council", council);
            model.addAttribute("pageTitle", "View Council (ID :" + council.getId() + ")");
            return "viewcouncil";

        } catch (CouncilNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }


        return "redirect:/councils";
    }





    @GetMapping("/councils/new")
    public String showRegistration(Model model) {
        model.addAttribute("council", new Council());
        model.addAttribute("pageTitle", "Add New Council");
        return "council_form";
    }

    @PostMapping("/councils/save")
    public String saveCouncil(Council council, RedirectAttributes ra) {
        councilService.save(council);
        ra.addFlashAttribute("message", "The council has been saved successfully!");
        return "redirect:/councils";
    }

    @GetMapping("/councils/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Council council = councilService.get(id);
            model.addAttribute("council", council);
            model.addAttribute("pageTitle", "Edit "+   council.getName() + " Council  (ID:" + council.getId() + ")");
            return "council_form";

        } catch (CouncilNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }


        return "redirect:/councils";
    }

    @GetMapping("/councils/delete/{id}")
    public String deleteCouncil(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            councilService.delete(id);
            ra.addFlashAttribute("message","The council with ID " + id + " has been deleted");
        } catch (CouncilNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/councils";

    }
}




