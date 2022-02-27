package museum.service.models.DTOs;

import lombok.Data;
import museum.service.models.enums.StaticResourceType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TourStaticContentDTO
{
    private Integer staticContentID;

    @NotBlank
    private String URI;

    private Boolean isYouTubeVideo;

    @NotNull
    private StaticResourceType resourceType;
}
