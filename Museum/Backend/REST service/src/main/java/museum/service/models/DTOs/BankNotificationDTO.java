package museum.service.models.DTOs;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BankNotificationDTO
{
    public static enum PaymentStatus {SUCCESSFUL, UNSUCCESSFUL};

    @NotNull
    private final PaymentStatus status;

    @NotBlank
    private final String id;

    @NotNull
    private final Integer receiverID;

    @NotNull
    private final BigDecimal amount;

    @NotNull
    private final LocalDateTime timestamp;

    @NotBlank
    private final String redirect, notificationURL;

    @NotNull
    private final Integer senderID;

    private final String scratchString;
}
