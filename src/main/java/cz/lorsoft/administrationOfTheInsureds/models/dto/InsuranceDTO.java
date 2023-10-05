package cz.lorsoft.administrationOfTheInsureds.models.dto;

import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class InsuranceDTO {
    private long insuranceId;
    @NotBlank(message = "Vyplňte kategorii pojištění")
    @NotNull(message = "Vyplňte kategorii pojištění")
    @Size(max = 32, message = "Název kategorie je příliš dlouhý")
    private String typeOfInsurance;

    @NotBlank(message = "Vyplňte podkategorii pojištění")
    @NotNull(message = "Vyplňte podkategorii pojištění")
    @Size(max = 32, message = "Název podkategorie je příliš dlouhý")
    private String variantInsurance;
    @Positive(message = "Vyplňte cenu za pojištění od")
    private int priceFrom;
    @Positive(message = "Vyplňte cenu za pojištění do")
    private int priceTo;
    @DecimalMin(value = "100", message = "Zadaná hodnota je příliš nízká")
    @DecimalMax(value = "1000", message = "Zadaná hodnota je příliš vysoká")
    private int enteredPrice = 200;
    @Positive(message = "Vyplňte hodnotu pojistného v případě vyplacení od")
    private int amountFrom;
    @Positive(message = "Vyplňte hodnotu pojistného v případě vyplacení do")
    private int amountTo;
    @DecimalMin(value = "100", message = "Zadaná hodnota je příliš nízká")
    @DecimalMax(value = "1000", message = "Zadaná hodnota je příliš vysoká")
    private int enteredAmount = 200;


}
