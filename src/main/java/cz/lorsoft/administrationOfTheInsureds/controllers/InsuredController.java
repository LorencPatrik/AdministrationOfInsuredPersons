package cz.lorsoft.administrationOfTheInsureds.controllers;

import cz.lorsoft.administrationOfTheInsureds.models.dto.InsuredDTO;
import cz.lorsoft.administrationOfTheInsureds.models.services.InsuredService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("insureds")
public class InsuredController {
    @Autowired
    private InsuredService insuredService;
    @GetMapping({"/", ""})
    public String renderInsureds(Model model) {
        List<InsuredDTO> insureds = insuredService.getAll();
        model.addAttribute("insureds", insureds);
        return "pages/insureds/index";
    }
    @GetMapping("/create")
    public String renderCreateInsuredForm(InsuredDTO insuredDTO){
        return "pages/insureds/create";
    }
    @PostMapping("/create")
    public String insuredFormHandle(@Valid @ModelAttribute InsuredDTO insuredDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors())
            return renderCreateInsuredForm(insuredDTO);
        insuredService.create(insuredDTO);
        redirectAttributes.addFlashAttribute("success", "Uživatel vytvořen.");
//        return "pages/insureds/index"; // funguje, ale nevypíše flash messages protože to nejde přes controller kde je uložena hodnota redirectAttributes
        return "redirect:/insureds"; // volání adresy controlleru nikoliv vracení samotné stránky view...
    }
    @GetMapping("{insuredId}")
    public String renderDetail (@PathVariable long insuredId, Model model){
        InsuredDTO insured = insuredService.getById(insuredId);
        model.addAttribute("insured", insured);
        return "pages/insureds/detail";
    }
}
