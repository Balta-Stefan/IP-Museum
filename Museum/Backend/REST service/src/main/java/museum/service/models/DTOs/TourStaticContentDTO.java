package museum.service.models.DTOs;

import lombok.Data;
import museum.service.models.enums.StaticResourceLocationType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TourStaticContentDTO
{
    private Integer staticContentID;

    @NotBlank
    private String URI;

    @NotNull
    private StaticResourceLocationType locationType;

    @NotNull
    private StaticResourceLocationType resourceType;
}
