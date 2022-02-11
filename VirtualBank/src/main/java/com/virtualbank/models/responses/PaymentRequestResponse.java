package com.virtualbank.models.responses;

import com.virtualbank.models.requests.PaymentRequest;
import lombok.Data;


@Data
public class PaymentRequestResponse
{
    private final PaymentRequest request;
    private final String token;
}
