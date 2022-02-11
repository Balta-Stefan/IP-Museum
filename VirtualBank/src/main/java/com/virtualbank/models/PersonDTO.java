package com.virtualbank.models;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PersonDTO
{
    private Integer id;

    @NotBlank
    private String address, phone, country, city, firstName, lastName, pin;

    private String cardNumber;

    @NotBlank
    @Email
    private String email;

    private BigDecimal availableFunds;
    private Boolean enabled;

    @NotNull
    private CardType cardType;

    private LocalDate cardExpirationDate;
}
