package museum.service.models.DTOs;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FormTourDTO
{
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate startDate;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private final LocalTime startTime;

    @NotNull
    private final Integer durationHours;

    @NotNull
    private final BigDecimal price;

    @Size(min = 5, max = 10)
    private final MultipartFile[] pictures;

    private final MultipartFile video;
    private final String youtubeLink;
}
