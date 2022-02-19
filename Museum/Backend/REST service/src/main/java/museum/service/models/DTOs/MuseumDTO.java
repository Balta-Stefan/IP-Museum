package museum.service.models.DTOs;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class MuseumDTO
{
    private Integer museumID;

    @NotBlank
    private String name, address, phone, city, country;

    @NotBlank
    private String type;

    @NotNull
    private BigDecimal longitude, latitude;
	
	private String thumbnail;
	
	private MuseumTypeDTO type;
}
