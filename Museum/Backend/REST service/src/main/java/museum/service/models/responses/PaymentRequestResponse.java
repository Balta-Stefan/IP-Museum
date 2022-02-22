package museum.service.models.responses;

import lombok.Data;
import museum.service.models.requests.PaymentRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PaymentRequestResponse
{
    public enum PaymentStatus {SUCCESSFUL, UNSUCCESSFUL};

    @NotNull
    private PaymentRequest request;

    @NotBlank
    private String redirectURL;

    @NotNull
    private PaymentStatus status;
}