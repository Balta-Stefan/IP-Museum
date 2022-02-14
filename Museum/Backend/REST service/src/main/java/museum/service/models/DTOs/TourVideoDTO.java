package museum.service.models.DTOs;

import lombok.Data;
import museum.service.models.enums.PresentationResourceType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TourVideoDTO
{
    private Integer videoID;

    @NotBlank
    private String videoURI;

    @NotNull
    private PresentationResourceType type;
}
