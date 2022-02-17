package museum.service.services;

import museum.service.models.DTOs.CityDTO;
import museum.service.models.DTOs.CountryDTO;
import museum.service.models.DTOs.RegionDTO;

import java.util.List;

public interface CountriesService
{
    List<CountryDTO> getCountries();
    List<RegionDTO> getRegions(String alpha2);
    List<CityDTO> getCities(String alpha2Code, String region);
}
