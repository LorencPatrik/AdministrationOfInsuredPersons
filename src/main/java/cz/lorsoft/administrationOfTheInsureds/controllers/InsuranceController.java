package cz.lorsoft.administrationOfTheInsureds.controllers;

import cz.lorsoft.administrationOfTheInsureds.models.dto.InsuranceDTO;
import cz.lorsoft.administrationOfTheInsureds.models.dto.InsuranceMapper;
import cz.lorsoft.administrationOfTheInsureds.models.exceptions.InsuranceNotFoundException;
import cz.lorsoft.administrationOfTheInsureds.models.services.InsuranceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("insurance")
public class InsuranceController {
    @Autowired
    private InsuranceService insuranceService;
    @Autowired
    private InsuranceMapper insuranceMapper;

    @GetMapping("/")
    public String renderInsurance(Model model){
        List<InsuranceDTO> allInsurance = insuranceService.getAll();
        model.addAttribute("allInsurance", allInsurance);
        return "pages/insurance/index";
    }
    @GetMapping("/create")
    public String renderCreateInsuranceForm(InsuranceDTO insuranceDTO){
        return "pages/insurance/create";
    }
    @PostMapping("/create")
    public String insuranceFormHandle(@Valid @ModelAttribute InsuranceDTO insuranceDTO, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors())
            return renderCreateInsuranceForm(insuranceDTO);
        insuranceService.create(insuranceDTO);
        redirectAttributes.addFlashAttribute("success", "Vytvořeno nové pojištění");
        return "redirect:/insurance/";
    }
    @GetMapping("{insuranceId}")
    public String renderInsuranceDetail (@PathVariable long insuranceId, Model model){
        InsuranceDTO oneInsurance = insuranceService.getById(insuranceId);
        model.addAttribute("oneInsurance", oneInsurance);
        return "pages/insurance/detail";
    }
    @GetMapping("edit/{insuranceId}")
    public String renderInsuranceEditForm(@PathVariable long insuranceId, @ModelAttribute InsuranceDTO insuranceDTO){
        InsuranceDTO fetchedOneInsurance = insuranceService.getById(insuranceId);
        insuranceMapper.updateInsuranceDTO(fetchedOneInsurance, insuranceDTO);
        return "pages/insurance/edit";
    }
    @PostMapping("edit/{insuranceId}")
    public String InsuranceEditFormHandle(@PathVariable long insuranceId, @ModelAttribute @Valid InsuranceDTO insuranceDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors())
            return renderInsuranceEditForm(insuranceId, insuranceDTO);
        insuranceDTO.setInsuranceId(insuranceId);
        insuranceService.edit(insuranceDTO);
        redirectAttributes.addFlashAttribute("success", "Data pojištění upravena.");
        return "redirect:/insurance/";
    }
    @GetMapping("delete/{insuranceId}")
    public String deleteInsurance(@PathVariable long insuranceId, RedirectAttributes redirectAttributes){
        insuranceService.delete(insuranceId);
        redirectAttributes.addFlashAttribute("success", "Pojištění vymazáno.");
        return "redirect:/insurance/";
    }
    @ExceptionHandler({InsuranceNotFoundException.class})
    public String handleInsuranceNotFoundException(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error", "Pojištění nenalezeno...");
        return "redirect:/insurance/";
    }
}
