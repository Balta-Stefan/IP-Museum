package museum.service.models.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class WeatherDTO
{
    @Data
    @NoArgsConstructor
    public static class Weather
    {
        private Integer id;
        private String main;
        private String description;
        private String icon;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    public static class Main
    {
        private Double temp;
        private Double feels_like;
        private Double temp_min;
        private Double temp_max;
        private Double pressure;
        private Double humidity;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    public static class Wind
    {
        private Double speed;
    }

    private List<Weather> weather;
    private Main main;
    private Wind wind;
    private String name;
}
