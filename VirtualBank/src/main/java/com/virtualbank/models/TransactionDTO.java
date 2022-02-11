package com.virtualbank.models;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO
{
    private final String id;
    private final Integer receiverID;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;
    private final String redirect, notificationURL;
    private final Integer senderID;
}
