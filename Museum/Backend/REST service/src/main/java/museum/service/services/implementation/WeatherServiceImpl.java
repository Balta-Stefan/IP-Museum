package museum.service.services.implementation;

import lombok.extern.slf4j.Slf4j;
import museum.service.models.DTOs.WeatherDTO;
import museum.service.services.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.Duration;

@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService
{
    private final String url;

    private final String apiKey;

    private final int requestTimeoutSeconds = 10;

    private final WebClient webClient;

    public WeatherServiceImpl(@Value("${api.url.weather}") String url, @Value("${api.key.weather}") String apiKey)
    {
        this.url = url;
        this.apiKey = apiKey;
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    @Override
    public WeatherDTO getWeather(BigDecimal latitude, BigDecimal longitude)
    {
        try
        {
            // api.openweathermap.org/data/2.5/weather?lat={LATITUDE}&lon={LONGITUDE}&units=metric&lang=hr&appid={MY_KEY}

            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("lat", "{LATITUDE}")
                            .queryParam("lon", "{LONGITUDE}")
                            .queryParam("units", "{UNITS}")
                            .queryParam("lang", "{LANGUAGE}")
                            .queryParam("appid", "{API_KEY}")
                            .build(latitude.toString(), longitude.toString(), "metric", "hr", apiKey))
                    .retrieve()
                    .bodyToMono(WeatherDTO.class).block(Duration.ofSeconds(requestTimeoutSeconds));
        }
        catch(Exception e)
        {
            log.warn("Weather serviec has thrown an exception: ", e);
        }

        return null;
    }
}
