package com.virtualbank.models.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PaymentRequest
{
    private Integer requesterID;

    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String notifyEndpoint;

    private Integer customerID; // used by the requester
}
