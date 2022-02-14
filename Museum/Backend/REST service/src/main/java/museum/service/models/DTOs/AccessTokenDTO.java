package museum.service.models.DTOs;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AccessTokenDTO
{
    @NotNull
    private UUID token;

    @NotNull
    private Integer userID;

    @NotNull
    private LocalDateTime created, validUntil;

    @NotNull
    private Boolean valid;
}
