package museum.service.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest
{
    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String notifyEndpoint;

    private String scratchString;
}