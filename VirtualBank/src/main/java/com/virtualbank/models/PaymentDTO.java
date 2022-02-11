package com.virtualbank.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class PaymentDTO
{
    @NotBlank
    private final String firstName, lastName;

    @NotBlank
    private final String cardNumber;

    @NotNull
    private final CardType cardType;

    @NotNull
    private final LocalDate cardExpirationDate;

    @NotBlank
    private final String pin;
}
