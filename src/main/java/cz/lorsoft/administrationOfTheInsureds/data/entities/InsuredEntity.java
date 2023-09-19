package cz.lorsoft.administrationOfTheInsureds.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "insured")
@Getter
@Setter
public class InsuredEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long insuredId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surName;
    @Column(nullable = false)
    private String email;
//    private Date dateOfBrith;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String streetAndNumber;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String zipCode;
}