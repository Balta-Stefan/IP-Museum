package museum.service.models.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CountryDTO
{
    @Data
    @NoArgsConstructor
    public static class CountryName
    {
        private String common;
    }
    private String cca2;
    private CountryName name;
}
