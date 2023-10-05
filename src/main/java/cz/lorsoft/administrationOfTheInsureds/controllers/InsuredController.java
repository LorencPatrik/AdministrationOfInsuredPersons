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
    private boolean viewPriceEntry = true; // ovlivňuje zobrazení volby ceny jednotlivého pojištění nebo vypočtenou cenu dle zadání
    private boolean showSummary = false; // a naopak ovlivňuje zobrazení souhrnu po zadání ceny pojištění

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
    @GetMapping("chooseInsurance/{insuredId}")
    public String chooseInsuranceForInsured(@PathVariable long insuredId, Model model){
        InsuredDTO insured = insuredService.getById(insuredId);
        model.addAttribute("insured", insured);
        List<String> allUniqueInsuranceStrings = insuranceService.getAllUnique();
        List<InsuranceDTO> allInsuranceDTO = insuranceService.getAll();
        model.addAttribute("allUniqueInsuranceStrings", allUniqueInsuranceStrings);
        model.addAttribute("allInsuranceDTO", allInsuranceDTO);
        // nenašel jsem jak přes thymeleaf iterovat dle dvou vlastností DTO kdy pro jednu chci zobrazit tlačítka k výběru
        // (typ pojištění) a podle druhé doplnit kategorii a tu zobrazit až po zvolení konkrétního typu. Proto si zde posílám
        // list Stringů s kategoriemi pojištění a při th:each jej porovnávám s aktuální hodnotou procházené vlastnosti a
        // vypisuju odpovídající kategorie...
        return "pages/insureds/chooseInsurance";
    }
    @RequestMapping("addInsurance/{insuranceId}/{insuredId}")
    public String addInsuranceToInsured (@PathVariable(value = "insuranceId") long insuranceId, @PathVariable(value = "insuredId") long insuredId, Model model){
        InsuredDTO insured = insuredService.getById(insuredId);
        InsuranceDTO insurance = insuranceService.getById(insuranceId);
        model.addAttribute("insurance", insurance);
        model.addAttribute("insured",  insured);
        model.addAttribute("viewPriceEntry", viewPriceEntry);
        model.addAttribute("showSummary", showSummary);
        return "pages/insureds/addInsurance";
    }
    @PostMapping("addInsurance/{insuranceId}/{insuredId}")
    public String handleInsuranceToInsured (@PathVariable(value = "insuranceId") long insuranceId, @PathVariable(value = "insuredId")
                                            long insuredId, Model model, int priceOrAmount, String radioButton){
        InsuredDTO insured = insuredService.getById(insuredId);
        InsuranceDTO insurance = insuranceService.getById(insuranceId);
        model.addAttribute("insurance", insurance);
        model.addAttribute("insured",  insured);
        if (radioButton.equals("price") && (priceOrAmount < insurance.getPriceFrom() || priceOrAmount > insurance.getPriceTo())) {
            model.addAttribute("error", "Price mimo rozsah. Prosím opravte");
            return addInsuranceToInsured(insurance.getInsuranceId(), insured.getInsuredId(), model);
        } else if (radioButton.equals("amount") && (priceOrAmount < insurance.getAmountFrom() || priceOrAmount > insurance.getAmountTo())) {
            model.addAttribute("error", "Amount mimo rozsah. Prosím opravte");
            return addInsuranceToInsured(insurance.getInsuranceId(), insured.getInsuredId(), model);
        }
        viewPriceEntry = false;
        showSummary = true;
        model.addAttribute("radioButton", radioButton);
        model.addAttribute("enteredValue", priceOrAmount);
        model.addAttribute("viewPriceEntry", viewPriceEntry);
        model.addAttribute("showSummary", showSummary);

        return "pages/insureds/addInsurance";
    }


    @ExceptionHandler({InsuredNotFoundException.class})
    public String handleInsuredNotFoundException(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error", "Pojištěný nenalezen...");
        return "redirect:/insureds/";
    }

}
