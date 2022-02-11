package com.virtualbank.models.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "transactions")
public class TransactionEntity
{
    @GeneratedValue
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Basic
    @Column(name = "amount", nullable = false, precision = 4)
    private BigDecimal amount;

    @Basic
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Basic
    @Column(name = "redirect", nullable = false, length = 255)
    private String redirect;

    @Basic
    @Column(name = "notificationURL", nullable = true, length = 255)
    private String notificationUrl;

    @ManyToOne
    @JoinColumn(name = "receiver", referencedColumnName = "id", nullable = false)
    private CompanyEntity receiver;

    @ManyToOne
    @JoinColumn(name = "sender", referencedColumnName = "id")
    private PersonEntity payer;

}
