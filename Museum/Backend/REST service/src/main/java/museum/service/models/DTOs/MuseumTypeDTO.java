package museum.service.models.DTOs;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MuseumTypeDTO
{
    private Integer museumTypeID;

    @NotBlank
    private String type;
}
