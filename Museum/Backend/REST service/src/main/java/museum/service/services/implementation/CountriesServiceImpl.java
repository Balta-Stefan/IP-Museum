package museum.service.services.implementation;

import museum.service.models.DTOs.CityDTO;
import museum.service.models.DTOs.CountryDTO;
import museum.service.models.DTOs.RegionDTO;
import museum.service.services.CountriesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CountriesServiceImpl implements CountriesService
{
    private final String countriesApiURL;
    private final String alpha2RegionURL;
    private final String alpha2AndRegion2CitiesURL;


    private final String alpha2ConverterApiKey;

    private final int requestTimeoutSeconds = 10;

    private final WebClient webclient;

    public CountriesServiceImpl(@Value("${api.key.alpha_2_code_converter}") String alpha2ConverterApiKey,
                                @Value("${countries-api-url}") String countriesApiURL,
                                @Value("${alpha2Region-api-url}") String alpha2RegionURL,
                                @Value("${alpha2AndRegion2Cities-api-url}") String alpha2AndRegion2CitiesURL)
    {
        this.alpha2ConverterApiKey = alpha2ConverterApiKey;
        this.countriesApiURL = countriesApiURL;
        this.alpha2RegionURL = alpha2RegionURL;
        this.alpha2AndRegion2CitiesURL = alpha2AndRegion2CitiesURL;

        webclient = WebClient.builder().build();
    }

    @Override
    public Mono<CountryDTO[]> getCountries()
    {
        return webclient.get()
                .uri(countriesApiURL)
                .retrieve()
                .bodyToMono(CountryDTO[].class);

    }

    @Override
    public Mono<RegionDTO[]> getRegions(String alpha2)
    {
        return webclient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(alpha2RegionURL + "/{COUNTRY_CODE}/all/")
                        .queryParam("key", "{API_KEY}")
                        .build(alpha2, alpha2ConverterApiKey))
                .retrieve()
                .bodyToMono(RegionDTO[].class);

    }

    @Override
    public Mono<CityDTO[]> getCities(String alpha2Code, String region)
    {
        return webclient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(alpha2AndRegion2CitiesURL + alpha2Code + "/search/")
                        .queryParam("region", "{region}")
                        .queryParam("key", "{API_KEY}")
                        .build(region, alpha2ConverterApiKey))
                .retrieve()
                .bodyToMono(CityDTO[].class);
    }
}
