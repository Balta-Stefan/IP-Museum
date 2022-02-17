package museum.service.models.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CityDTO
{
    private String city, region, country;
    private BigDecimal latitude, longitude;
}

