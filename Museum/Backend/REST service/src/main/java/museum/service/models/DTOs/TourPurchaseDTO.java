package museum.service.models.DTOs;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TourPurchaseDTO
{
    @NotNull
    private UUID purchaseID;

    @NotNull
    private Integer tour, user;

    @NotNull
    private LocalDateTime purchased;

    private LocalDateTime paid;
}
