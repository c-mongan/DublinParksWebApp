package com.example.dublinparkswebapp.park;

import com.example.dublinparkswebapp.council.Council;
import com.example.dublinparkswebapp.council.CouncilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
public class ParkController {
    @Autowired
    private ParkService parkService;

    @Autowired
    private CouncilService councilService;

    @GetMapping("/parks")
    public String showParkList(Model model) {
        List<Park> listOfParks = parkService.listAll();
        model.addAttribute("listOfParks", listOfParks);
        return "parks";

    }


//    @GetMapping("/userlogin")
//    public String showParkListUser(Model model) {
//        List<Park> listOfParks = parkService.listAll();
//        model.addAttribute("listOfParks", listOfParks);
//        return "userparks";
//
//    }


    @GetMapping("/parks/fingal")
    public String showParksFingal(Model model) {
        String councilName = "Fingal County Council";
        List<Park> listOfParks = parkService.findAllFingal(councilName);
        model.addAttribute("listOfParks", listOfParks);
        return "parks";

    }

//
//    @GetMapping("parks/view/fingal")
//    public String showFingal(Model model) {
//        String councilName = "Fingal County Council";
//        List<Park> listOfParks = parkService.findAllFingal(councilName);
//        model.addAttribute("listOfParks", listOfParks);
//        return "userparks";
//
//    }


    @GetMapping("/parks/dublincity")
    public String showParksDublinCity(Model model) {
        String councilName = "Dublin City Council";
        List<Park> listOfParks = parkService.findAllFingal(councilName);
        model.addAttribute("listOfParks", listOfParks);
        return "parks";

    }


    @GetMapping("/parks/dlr")
    public String showParksDLR(Model model) {
        String councilName = "Dún Laoghaire-Rathdown County Council";
        List<Park> listOfParks = parkService.findAllFingal(councilName);
        model.addAttribute("listOfParks", listOfParks);
        return "parks";

    }


//
//    @GetMapping("/parks/view/dlr")
//    public String showDLR(Model model) {
//        String councilName = "Dún Laoghaire-Rathdown County Council";
//        List<Park> listOfParks = parkService.findAllFingal(councilName);
//        model.addAttribute("listOfParks", listOfParks);
//        return "parksview";
//
//    }

//
//    @GetMapping("/parks/view/dublincity")
//    public String showDublinCity(Model model) {
//        String councilName = "Dublin City Council";
//        List<Park> listOfParks = parkService.findAllFingal(councilName);
//        model.addAttribute("listOfParks", listOfParks);
//        return "parksview";
//
//    }


    @GetMapping("/parks/view/{id}")
    public String showParkById(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Park park = parkService.get(id);
            model.addAttribute("park", park);
            model.addAttribute("pageTitle", "View Park (ID :" + park.getId() + ")");
            return "viewpark";

        } catch (ParkNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }


        return "redirect:/parks";
    }




    @GetMapping("users/parks/view/{id}")
    public String showParksById(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Park park = parkService.get(id);
            model.addAttribute("park", park);
            model.addAttribute("pageTitle", "View Park (ID :" + park.getId() + ")");
            return "viewparkbyid";

        } catch (ParkNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());

    }

        return "redirect:/userlogin";
    }



    @GetMapping("/parks/new")
    public String showRegistration(Model model) {
        List<Council> listOfCouncils = councilService.listAll();
        model.addAttribute("park", new Park());
        model.addAttribute("listOfCouncils", listOfCouncils);
        model.addAttribute("pageTitle", "Add New Park");
        return "park_form";
    }

    @PostMapping("/parks/save")
    public String savePark(Park park, RedirectAttributes ra) {

            parkService.save(park);


        ra.addFlashAttribute("message", "The park has been saved successfully!");
        return "redirect:/parks";
    }

    @GetMapping("/parks/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {

            Park park = parkService.get(id);
            List<Council> listOfCouncils = councilService.listAll();
            model.addAttribute("listOfCouncils", listOfCouncils);
            model.addAttribute("park", park);
            model.addAttribute("pageTitle", "Edit "+   park.getName() + " Park  (ID:" + park.getId() + ")");
            return "park_form";

        } catch (ParkNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }


        return "redirect:/parks";
    }

    @GetMapping("/parks/delete/{id}")
    public String deletePark(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            parkService.delete(id);
            ra.addFlashAttribute("message","The park with ID " + id + " has been deleted");
        } catch (ParkNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/parks";

    }
}




