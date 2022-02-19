package museum.service.services.implementation;

import museum.service.models.DTOs.WeatherDTO;
import museum.service.services.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.Duration;

@Service
public class WeatherServiceImpl implements WeatherService
{
    @Value("${api.url.weather}")
    private String url;

    @Value("${api.key.weather}")
    private String apiKey;

    private final int requestTimeoutSeconds = 10;

    @Override
    public WeatherDTO getWeather(BigDecimal latitude, BigDecimal longitude)
    {
        // api.openweathermap.org/data/2.5/weather?lat={LATITUDE}&lon={LONGITUDE}&units=metric&lang=hr&appid={MY_KEY}
        WebClient client = WebClient.builder()
                .baseUrl(url)
                .build();

        WeatherDTO weather = client.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("lat", "{LATITUDE}")
                        .queryParam("lon", "{LONGITUDE}")
                        .queryParam("units", "{UNITS}")
                        .queryParam("lang", "{LANGUAGE}")
                        .queryParam("appid", "{API_KEY}")
                        .build(latitude.toString(), longitude.toString(), "metric", "hr", apiKey))
                .retrieve()
                .bodyToMono(WeatherDTO.class).block(Duration.ofSeconds(requestTimeoutSeconds));

        return weather;
    }
}
