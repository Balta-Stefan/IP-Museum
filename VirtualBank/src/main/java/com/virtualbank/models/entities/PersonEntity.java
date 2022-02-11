package com.virtualbank.models.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "persons")
@PrimaryKeyJoinColumn(name = "id")
public class PersonEntity extends ClientEntity
{
    @Basic
    @Column(name = "firstName", nullable = false, length = 45)
    private String firstName;

    @Basic
    @Column(name = "lastName", nullable = false, length = 45)
    private String lastName;

    @Basic
    @Column(name = "cardNumber", nullable = false, unique = true, length = 16)
    @Length(min = 16, max = 16)
    private String cardNumber;

    @Basic
    @Column(name = "cardType", nullable = false, length = 16)
    private String cardType;

    @Basic
    @Column(name = "cardExpirationDate", nullable = false)
    private LocalDate cardExpirationDate;

    @Basic
    @Column(name = "pin", nullable = false, length = 4)
    private String pin;

    @OneToMany(mappedBy = "payer")
    private List<TransactionEntity> transactions;

}
