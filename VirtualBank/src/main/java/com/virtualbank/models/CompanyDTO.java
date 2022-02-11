package com.virtualbank.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CompanyDTO
{
    private Integer id;

    @NotBlank
    private String address, phone, country, city, email, name;

    private String token;

    private BigDecimal availableFunds;
    private Boolean enabled;
}
