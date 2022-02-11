package com.virtualbank.services.impl;

import com.virtualbank.models.CompanyDTO;
import com.virtualbank.models.entities.CompanyEntity;
import com.virtualbank.repositories.CompanyRepository;
import com.virtualbank.services.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService
{
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    
    @PersistenceContext
    private EntityManager entityManager;

    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder)
    {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CompanyDTO create(@Valid CompanyDTO companyDTO)
    {
        CompanyEntity companyEntity = modelMapper.map(companyDTO, CompanyEntity.class);
        companyEntity.setId(null);
        companyEntity.setAvailableFunds(BigDecimal.valueOf(0));
        companyEntity.setEnabled(true);

        String token = UUID.randomUUID().toString();
        companyEntity.setToken(passwordEncoder.encode(token));

        companyEntity = companyRepository.saveAndFlush(companyEntity);
        entityManager.refresh(companyEntity);

        CompanyDTO temp = modelMapper.map(companyEntity, CompanyDTO.class);
        temp.setToken(token);

        return temp;
    }
}
