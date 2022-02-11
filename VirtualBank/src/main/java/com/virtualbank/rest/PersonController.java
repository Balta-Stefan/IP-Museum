package com.virtualbank.rest;

import com.virtualbank.models.PersonDTO;
import com.virtualbank.services.PersonService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController
{
    private final PersonService personService;

    public PersonController(PersonService personService)
    {
        this.personService = personService;
    }

    @PostMapping
    public PersonDTO create(@RequestBody PersonDTO personDTO) throws Exception
    {
        return personService.create(personDTO);
    }
}
