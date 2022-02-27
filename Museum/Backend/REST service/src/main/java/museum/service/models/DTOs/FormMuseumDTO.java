package museum.service.models.DTOs;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class FormMuseumDTO
{
    @NotBlank
    private String name, address, phone, city, country;

    @NotBlank
    @Size(min = 2, max = 2)
    private String countryAlpha2Code;

    @NotNull
    private BigDecimal longitude, latitude;

    private String thumbnail;

    private Integer type;
}
