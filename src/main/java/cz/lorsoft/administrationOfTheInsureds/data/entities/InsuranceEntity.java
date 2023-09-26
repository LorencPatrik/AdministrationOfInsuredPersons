package cz.lorsoft.administrationOfTheInsureds.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "insurance")
@Getter
@Setter
public class InsuranceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long insuranceId;
    @Column(nullable = false)
    private String typeOfInsurance;
    @Column(nullable = false)
    private String variantInsurance;
    @Column
    private int priceFrom;
    @Column
    private int priceTo;
    @Column
    private int amountFrom;
    @Column
    private int amountTo;
}
