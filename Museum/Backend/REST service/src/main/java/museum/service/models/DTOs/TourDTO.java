package museum.service.models.DTOs;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TourDTO
{
    private Integer tourID;

    private MuseumDTO museum;

    @NotNull
    private LocalDateTime startTimestamp, endTimestamp;

    @NotNull
    private BigDecimal price;

    private List<TourStaticContentDTO> staticContent;
}
