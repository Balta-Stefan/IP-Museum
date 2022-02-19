package museum.service.services;

import museum.service.models.DTOs.WeatherDTO;

import java.math.BigDecimal;


public interface WeatherService
{
    WeatherDTO getWeather(BigDecimal latitude, BigDecimal longitude);
}
