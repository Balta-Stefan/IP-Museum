package museum.service.services.implementation;

import museum.service.models.DTOs.CountryDTO;
import museum.service.services.CountriesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Service
public class CountriesServiceImpl implements CountriesService
{
    @Value("${countries.api.url}")
    private String countriesApiURL;

    private final int getCountriesTimeoutSeconds = 10;

    @Override
    public List<CountryDTO> getCountries()
    {
        WebClient client = WebClient.builder()
                .baseUrl(countriesApiURL)
                .build();

        CountryDTO[] countries = client.get()
                .retrieve()
                .bodyToMono(CountryDTO[].class).block(Duration.ofSeconds(getCountriesTimeoutSeconds));

        return Arrays.asList(countries);
    }
}
