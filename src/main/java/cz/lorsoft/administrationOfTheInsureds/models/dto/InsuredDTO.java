package cz.lorsoft.administrationOfTheInsureds.models.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class InsuredDTO {
    private Long insuredId;
    @NotBlank(message = "Vyplňte jméno")
    @NotNull(message = "Vyplňte jméno")
    @Size(max = 16, message = "Jméno je příliš dlouhé")
    private String name;
    @NotBlank(message = "Vyplňte příjmení")
    @NotNull(message = "Vyplňte příjmení")
    @Size(max = 32, message = "Příjmení je příliš dlouhé")
    private String surName;
    @NotBlank(message = "Vyplňte email")
    @NotNull(message = "Vyplňte email")
    @Size(max = 32, message = "Email je příliš dlouhý")
    private String email;

//    @NotBlank(message = "Vyplňte datum narození")
//    @NotNull(message = "Vyplňte datum narození")
//    private LocalDate dateOfBrith;
//    @Future(message = "Vyplňte datum začátku pojištění (nelze uzavřít zpětně...")
//    private LocalDate dateFrom;
//    @Future(message = "Vyplňte datum konce pojištění")
//    private LocalDate dateTo;
    @NotBlank(message = "Vyplňte tel. číslo")
    @NotNull(message = "Vyplňte tel. číslo")
    @Size(max = 16, min = 6, message = "Tel. číslo je příliš krátké nebo dlouhé 6 - 16 zanků")
    private String phone;
    @NotBlank(message = "Vyplňte název ulice a číslo popisné")
    @NotNull(message = "Vyplňte název ulice a číslo popisné")
    @Size(max = 32, message = "Název ulice a číslo popisné je příliš dlouhé")
    private String streetAndNumber;
    @NotBlank(message = "Vyplňte název města nebo obce")
    @NotNull(message = "Vyplňte název města nebo obce")
    @Size(max = 32, message = "Název města nebo obce je příliš dlouhý")
    private String city;
    @NotBlank(message = "Vyplňte poštovní směrovací číslo")
    @NotNull(message = "Vyplňte poštovní směrovací číslo")
    @Size(max = 8, message = "Poštovní směrovací číslo je příliš dlouhé max 8. znaků")
    private String zipCode;
}
