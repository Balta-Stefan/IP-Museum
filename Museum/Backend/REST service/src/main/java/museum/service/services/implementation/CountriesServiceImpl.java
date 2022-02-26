package museum.service.services.implementation;

import museum.service.models.DTOs.CityDTO;
import museum.service.models.DTOs.CountryDTO;
import museum.service.models.DTOs.RegionDTO;
import museum.service.services.CountriesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CountriesServiceImpl implements CountriesService
{
    private final String countriesApiURL = "https://restcountries.com/v3.1/region/europe";
    private final String alpha2RegionURL = "http://battuta.medunes.net/api/region";
    private final String alpha2AndRegion2CitiesURL = "http://battuta.medunes.net/api/city/";

    @Value("${api.key.alpha_2_code_converter}")
    private String alpha2ConverterApiKey;

    private final int requestTimeoutSeconds = 10;

    @Override
    public List<CountryDTO> getCountries()
    {
        try
        {
            WebClient client = WebClient.builder()
                    .baseUrl(countriesApiURL)
                    .build();

            CountryDTO[] countries = client.get()
                    .retrieve()
                    .bodyToMono(CountryDTO[].class).block(Duration.ofSeconds(requestTimeoutSeconds));

            return Arrays.asList(countries);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    @Override
    public List<RegionDTO> getRegions(String alpha2)
    {
        try
        {
            WebClient client = WebClient.builder()
                    .baseUrl(alpha2RegionURL)
                    .build();

            RegionDTO[] regions = client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/{COUNTRY_CODE}/all/")
                            .queryParam("key", "{API_KEY}")
                            .build(alpha2, alpha2ConverterApiKey))
                    .retrieve()
                    .bodyToMono(RegionDTO[].class).block(Duration.ofSeconds(requestTimeoutSeconds));

            return Arrays.asList(regions);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    @Override
    public List<CityDTO> getCities(String alpha2Code, String region)
    {
        try
        {
            WebClient client = WebClient.builder()
                    .baseUrl(alpha2AndRegion2CitiesURL)
                    .build();

            CityDTO[] cities = client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path(alpha2Code + "/search/")
                            .queryParam("region", "{region}")
                            .queryParam("key", "{API_KEY}")
                            .build(region, alpha2ConverterApiKey))
                    .retrieve()
                    .bodyToMono(CityDTO[].class).block(Duration.ofSeconds(requestTimeoutSeconds));

            return Arrays.asList(cities);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
