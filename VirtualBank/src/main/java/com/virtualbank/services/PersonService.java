package com.virtualbank.services;

import com.virtualbank.models.PersonDTO;

public interface PersonService
{
    PersonDTO create(PersonDTO person) throws Exception;
}
