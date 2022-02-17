package museum.service.services;

import museum.service.models.DTOs.CountryDTO;

import java.util.List;

public interface CountriesService
{
    List<CountryDTO> getCountries();
}
