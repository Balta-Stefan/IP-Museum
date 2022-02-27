package museum.service.services;

import museum.service.models.DTOs.CityDTO;
import museum.service.models.DTOs.CountryDTO;
import museum.service.models.DTOs.RegionDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CountriesService
{
    Mono<CountryDTO[]> getCountries();
    Mono<RegionDTO[]> getRegions(String alpha2);
    Mono<CityDTO[]> getCities(String alpha2Code, String region);
}
