package com.virtualbank.services.impl;

import com.virtualbank.models.ApiUserDetails;
import com.virtualbank.models.entities.CompanyEntity;
import com.virtualbank.services.ApiUserDetailsService;
import com.virtualbank.repositories.CompanyRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class ApiUserDetailsServiceImpl implements ApiUserDetailsService
{
    private final CompanyRepository companyRepository;

    public ApiUserDetailsServiceImpl(CompanyRepository clientsRepository)
    {
        this.companyRepository = clientsRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException
    {
        // load by user's token received via http basic authentication
        CompanyEntity company = companyRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException("Password incorrect"));
        ApiUserDetails userDetails = new ApiUserDetails(company.getEnabled(), name, company.getToken(), Collections.emptyList(), company.getId());

        return userDetails;
    }
}
