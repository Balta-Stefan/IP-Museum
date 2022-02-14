package museum.service.models.DTOs;

import lombok.Data;
import museum.service.models.enums.PresentationResourceType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TourPictureDTO
{
    private Integer pictureID;

    @NotBlank
    private String pictureURI;

    @NotNull
    private PresentationResourceType type;
}
