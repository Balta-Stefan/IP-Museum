package com.virtualbank.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "companies")
@PrimaryKeyJoinColumn(name = "id")
public class CompanyEntity extends ClientEntity
{
    @Basic
    @Column(name = "name", nullable = false, unique = true, length = 45)
    private String name;

    @Basic
    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @OneToMany(mappedBy = "receiver")
    private List<TransactionEntity> transactions;
}
