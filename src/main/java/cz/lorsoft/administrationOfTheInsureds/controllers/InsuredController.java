package cz.lorsoft.administrationOfTheInsureds.controllers;

import cz.lorsoft.administrationOfTheInsureds.models.dto.InsuranceDTO;
import cz.lorsoft.administrationOfTheInsureds.models.dto.InsuredDTO;
import cz.lorsoft.administrationOfTheInsureds.models.dto.InsuredMapper;
import cz.lorsoft.administrationOfTheInsureds.models.exceptions.InsuredNotFoundException;
import cz.lorsoft.administrationOfTheInsureds.models.services.InsuranceService;
import cz.lorsoft.administrationOfTheInsureds.models.services.InsuredService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.jar.Attributes;

@Controller
@RequestMapping("insureds")
public class InsuredController {
    @Autowired
    private InsuredService insuredService;
    @Autowired
    private InsuranceService insuranceService;
    @Autowired
    private InsuredMapper insuredMapper;

    @GetMapping("/")
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
        redirectAttributes.addFlashAttribute("success", "Vytvořen nový pojištěný.");
//        return "pages/insureds/index"; // funguje, ale nevypíše flash messages protože to nejde přes controller kde je uložena hodnota redirectAttributes
        return "redirect:/insureds/"; // volání adresy controlleru nikoliv vracení samotné stránky view...
    }
    @GetMapping("{insuredId}")
    public String renderDetail (@PathVariable long insuredId, Model model){
        InsuredDTO insured = insuredService.getById(insuredId);
        model.addAttribute("insured", insured);
        return "pages/insureds/detail";
    }
    @GetMapping("edit/{insuredId}")
    public String renderEditForm(@PathVariable long insuredId, @ModelAttribute InsuredDTO insuredDTO){
        InsuredDTO fetchedInsured = insuredService.getById(insuredId);
        insuredMapper.updateInsuredDTO(fetchedInsured, insuredDTO);
        return "pages/insureds/edit";
    }
    @PostMapping("edit/{insuredId}")
    public String InsuredEditFormHandle(@PathVariable long insuredId, @ModelAttribute @Valid InsuredDTO insuredDTO, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors())
            return renderEditForm(insuredId, insuredDTO);
        insuredDTO.setInsuredId(insuredId);
        insuredService.edit(insuredDTO);
        redirectAttributes.addFlashAttribute("success", "Data pojištěného upravena.");
        return "redirect:/insureds/";
    }
    @GetMapping("delete/{insuredId}")
    public String deleteInsured(@PathVariable long insuredId, RedirectAttributes redirectAttributes){
        insuredService.delete(insuredId);
        redirectAttributes.addFlashAttribute("success", "Pojištěný vymazán.");
        return "redirect:/insureds/";
    }
    @GetMapping("addInsurance/{insuredId}")
    public String addInsuranceToInsured(@PathVariable long insuredId, Model model){
        InsuredDTO insured = insuredService.getById(insuredId);
        model.addAttribute("insured", insured);
        List<String> allUniqueInsuranceStrings = insuranceService.getAllUnique();
        List<InsuranceDTO> allInsuranceDTO = insuranceService.getAll();
        model.addAttribute("allUniqueInsuranceStrings", allUniqueInsuranceStrings);
        model.addAttribute("allInsuranceDTO", allInsuranceDTO);
        return "pages/insureds/addInsurance";
    }

    @ExceptionHandler({InsuredNotFoundException.class})
    public String handleInsuredNotFoundException(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error", "Pojištěný nenalezen...");
        return "redirect:/insureds/";
    }

}
