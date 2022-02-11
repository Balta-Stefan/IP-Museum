package com.virtualbank.services;

import com.virtualbank.exceptions.InsufficientFunds;
import com.virtualbank.exceptions.NotFoundException;
import com.virtualbank.exceptions.TransactionAlreadyDone;
import com.virtualbank.models.PaymentDTO;
import com.virtualbank.models.TransactionDTO;
import com.virtualbank.models.requests.PaymentRequest;
import com.virtualbank.models.responses.PaymentRequestResponse;

public interface PaymentsService
{
    PaymentRequestResponse requestPayment(PaymentRequest request) throws NotFoundException;
    TransactionDTO performPayment(String token, PaymentDTO payer) throws NotFoundException, InsufficientFunds, TransactionAlreadyDone;
}
