package com.virtualbank.rest;

import com.virtualbank.models.CompanyDTO;
import com.virtualbank.services.CompanyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyController
{
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService)
    {
        this.companyService = companyService;
    }

    @PostMapping
    public CompanyDTO createCompany(@RequestBody CompanyDTO companyDTO)
    {
        return companyService.create(companyDTO);
    }
}
