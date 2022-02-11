package com.virtualbank.models.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="clients")
public class ClientEntity
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "address", nullable = false, length = 45)
    private String address;

    @Basic
    @Column(name = "phone", nullable = false, length = 45)
    private String phone;

    @Basic
    @Column(name = "country", nullable = false, length = 45)
    private String country;

    @Basic
    @Column(name = "city", nullable = false, length = 45)
    private String city;

    @Basic
    @Column(name = "email", nullable = false, unique = true, length = 45)
    private String email;

    @Basic
    @Column(name = "availableFunds", nullable = false, precision = 4)
    private BigDecimal availableFunds;

    @Basic
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;
}
