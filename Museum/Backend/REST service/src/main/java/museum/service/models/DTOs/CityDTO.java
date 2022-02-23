package museum.service.models.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class CityDTO
{
    private String city, region, country;
    private BigDecimal latitude, longitude;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityDTO cityDTO = (CityDTO) o;
        return city.equals(cityDTO.city) && country.equals(cityDTO.country);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(city, country);
    }
}

