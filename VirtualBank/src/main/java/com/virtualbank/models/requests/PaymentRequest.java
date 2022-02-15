package com.virtualbank.models.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class PaymentRequest
{
    private Integer requesterID;

    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String notifyEndpoint;

    @Size(max = 255)
    private String scratchString; // used by the requester
}
