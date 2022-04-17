package com.virtualbank.services.impl;

import com.virtualbank.models.PersonDTO;
import com.virtualbank.models.entities.PersonEntity;
import com.virtualbank.services.PersonService;
import com.virtualbank.repositories.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.Random;


@Service
@Transactional
public class PersonServiceImpl implements PersonService
{
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public PersonServiceImpl(PersonRepository personRepository, ModelMapper modelMapper)
    {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }

    private String generateCardNumber()
    {
        Random rnd = new Random();

        StringBuilder tempCardNumber = new StringBuilder();
        for(int j = 0; j < 16; j++)
        {
            tempCardNumber.append(rnd.nextInt(10));
        }

        return tempCardNumber.toString();
    }

    private String generatePin()
    {
        Random rnd = new Random();
        return Integer.toString(1000 + rnd.nextInt(8999));
    }

    @Override
    public PersonDTO create(@Valid PersonDTO personDTO) throws Exception
    {
        PersonEntity personEntity = modelMapper.map(personDTO, PersonEntity.class);
        personEntity.setId(null);
        personEntity.setEnabled(true);
        personEntity.setAvailableFunds(BigDecimal.valueOf(0));
        personEntity.setCardExpirationDate(LocalDate.now().plusYears(2));
        personEntity.setCardNumber(this.generateCardNumber());
        personEntity.setPin(this.generatePin());

        personEntity = personRepository.saveAndFlush(personEntity);
        entityManager.refresh(personEntity);


        return modelMapper.map(personEntity, PersonDTO.class);
    }
}
