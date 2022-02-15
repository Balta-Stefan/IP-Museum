package museum.service.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PaymentRequest
{
    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String notifyEndpoint;

    private String scratchString;
}