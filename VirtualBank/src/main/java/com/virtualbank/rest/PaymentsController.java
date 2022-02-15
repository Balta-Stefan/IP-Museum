package com.virtualbank.rest;

import com.virtualbank.exceptions.ForbiddenException;
import com.virtualbank.exceptions.InsufficientFunds;
import com.virtualbank.exceptions.NotFoundException;
import com.virtualbank.exceptions.TransactionAlreadyDone;
import com.virtualbank.models.ApiUserDetails;
import com.virtualbank.models.PaymentDTO;
import com.virtualbank.models.TransactionDTO;
import com.virtualbank.models.requests.PaymentRequest;
import com.virtualbank.models.responses.PaymentRequestResponse;
import com.virtualbank.services.PaymentsService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentsController
{
    private final PaymentsService paymentsService;

    public PaymentsController(PaymentsService paymentsService)
    {
        this.paymentsService = paymentsService;
    }

    @PatchMapping("/{token}")
    public TransactionDTO pay(@PathVariable String token, @RequestBody @Valid PaymentDTO payment) throws NotFoundException, TransactionAlreadyDone, InsufficientFunds, ForbiddenException
    {
        return paymentsService.performPayment(token, payment);
    }

    @PostMapping
    public PaymentRequestResponse requestPayment(@RequestBody @Valid PaymentRequest request, Authentication authentication) throws NotFoundException
    {
        ApiUserDetails details = (ApiUserDetails)(authentication.getPrincipal());

        request.setRequesterID(details.getId());

        return paymentsService.requestPayment(request);
    }

}
